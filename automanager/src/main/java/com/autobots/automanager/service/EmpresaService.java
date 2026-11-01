package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.EmpresaRequestDTO;
import com.autobots.automanager.dto.response.EmpresaResponseDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.excecoes.AssociacaoJaExistenteException;
import com.autobots.automanager.excecoes.AssociacaoNaoEncontradaException;
import com.autobots.automanager.excecoes.EmpresaNaoEncontradaException;
import com.autobots.automanager.excecoes.MercadoriaNaoEncontradaException;
import com.autobots.automanager.excecoes.RazaoSocialDuplicadaException;
import com.autobots.automanager.excecoes.ServicoNaoEncontradoException;
import com.autobots.automanager.excecoes.UsuarioNaoEncontradoException;
import com.autobots.automanager.mapper.EmpresaMapper;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.excecoes.ExclusaoNaoPermitidaException;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepositorio repositorio;

    @Autowired
    private EmpresaMapper mapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;

    @Autowired
    private ServicoRepositorio servicoRepositorio;

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    public List<EmpresaResponseDTO> obterEmpresas() {
        List<Empresa> empresas = repositorio.findAll();

        List<EmpresaResponseDTO> dtos = new ArrayList<>();

        for (Empresa empresa : empresas) {
            dtos.add(mapper.paraResponseDTO(empresa));
        }

        return dtos;
    }

    public EmpresaResponseDTO obterEmpresa(Long id) {
        Empresa empresa = repositorio.findById(id)
                .orElseThrow(() -> new EmpresaNaoEncontradaException(id));

        return mapper.paraResponseDTO(empresa);
    }

    public EmpresaResponseDTO cadastrarEmpresa(EmpresaRequestDTO dto) {
        if (repositorio.existsByRazaoSocial(dto.getRazaoSocial())) {
            throw new RazaoSocialDuplicadaException(dto.getRazaoSocial());
        }

        Empresa empresa = mapper.paraEntidade(dto);

        empresa = repositorio.save(empresa);

        return mapper.paraResponseDTO(empresa);
    }

    public EmpresaResponseDTO atualizarEmpresa(Long id, EmpresaRequestDTO dto) {
        Empresa empresa = repositorio.findById(id)
                .orElseThrow(() -> new EmpresaNaoEncontradaException(id));

        if (repositorio.existsByRazaoSocialAndIdNot(dto.getRazaoSocial(), id)) {
            throw new RazaoSocialDuplicadaException(dto.getRazaoSocial());
        }

        mapper.atualizarEntidade(empresa, dto);

        empresa = repositorio.save(empresa);

        return mapper.paraResponseDTO(empresa);
    }

    public void excluirEmpresa(Long id) {
        Empresa empresa = repositorio.findById(id)
                .orElseThrow(() -> new EmpresaNaoEncontradaException(id));

        for (Usuario usuario : empresa.getUsuarios()) {
            if (vendaRepositorio.existsByCliente_IdAndEmpresa_IdNot(usuario.getId(), id)) {
                throw new ExclusaoNaoPermitidaException(
                        "O usuário " + usuario.getId() + " (" + usuario.getNome() + ") é cliente de "
                        + "vendas de outra empresa. Exclua essas vendas antes de excluir a empresa " + id);
            }
        }

        vendaRepositorio.deleteAll(vendaRepositorio.findByEmpresa_Id(id));

        for (Usuario usuario : empresa.getUsuarios()) {
            veiculoRepositorio.deleteAll(
                    veiculoRepositorio.findByProprietario_Id(usuario.getId()));
        }

        for (Mercadoria mercadoria : empresa.getMercadorias()) {
            Long mercadoriaId = mercadoria.getId();

            usuarioRepositorio.findByMercadorias_Id(mercadoriaId)
                    .ifPresent(usuario -> {
                        usuario.getMercadorias().removeIf(m -> m.getId().equals(mercadoriaId));
                        usuarioRepositorio.save(usuario);
                    });
        }
        repositorio.delete(empresa);
    }

    public EmpresaResponseDTO associarUsuario(Long empresaId, Long usuarioId) {
        Empresa empresa = buscarEmpresa(empresaId);

        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        if (usuario.getEmpresa() != null) {
            throw new AssociacaoJaExistenteException(
                    "O usuário " + usuarioId + " já está associado a uma empresa");
        }

        usuario.setEmpresa(empresa);
        usuarioRepositorio.save(usuario);

        empresa.getUsuarios().add(usuario);

        return mapper.paraResponseDTO(empresa);
    }

    public void desassociarUsuario(Long empresaId, Long usuarioId) {
        buscarEmpresa(empresaId);

        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        if (usuario.getEmpresa() == null || !usuario.getEmpresa().getId().equals(empresaId)) {
            throw new AssociacaoNaoEncontradaException(
                    "O usuário " + usuarioId + " não está associado à empresa " + empresaId);
        }

        usuario.setEmpresa(null);
        usuarioRepositorio.save(usuario);
    }

    public EmpresaResponseDTO associarMercadoria(Long empresaId, Long mercadoriaId) {
        Empresa empresa = buscarEmpresa(empresaId);

        Mercadoria mercadoria = mercadoriaRepositorio.findById(mercadoriaId)
                .orElseThrow(() -> new MercadoriaNaoEncontradaException(mercadoriaId));

        if (mercadoria.getEmpresa() != null) {
            throw new AssociacaoJaExistenteException(
                    "A mercadoria " + mercadoriaId + " já está associada a uma empresa");
        }

        mercadoria.setEmpresa(empresa);
        mercadoriaRepositorio.save(mercadoria);

        empresa.getMercadorias().add(mercadoria);

        return mapper.paraResponseDTO(empresa);
    }

    public void desassociarMercadoria(Long empresaId, Long mercadoriaId) {
        buscarEmpresa(empresaId);

        Mercadoria mercadoria = mercadoriaRepositorio.findById(mercadoriaId)
                .orElseThrow(() -> new MercadoriaNaoEncontradaException(mercadoriaId));

        if (mercadoria.getEmpresa() == null || !mercadoria.getEmpresa().getId().equals(empresaId)) {
            throw new AssociacaoNaoEncontradaException(
                    "A mercadoria " + mercadoriaId + " não está associada à empresa " + empresaId);
        }

        mercadoria.setEmpresa(null);
        mercadoriaRepositorio.save(mercadoria);
    }

    public EmpresaResponseDTO associarServico(Long empresaId, Long servicoId) {
        Empresa empresa = buscarEmpresa(empresaId);

        Servico servico = servicoRepositorio.findById(servicoId)
                .orElseThrow(() -> new ServicoNaoEncontradoException(servicoId));

        if (servico.getEmpresa() != null) {
            throw new AssociacaoJaExistenteException(
                    "O serviço " + servicoId + " já está associado a uma empresa");
        }

        servico.setEmpresa(empresa);
        servicoRepositorio.save(servico);

        empresa.getServicos().add(servico);

        return mapper.paraResponseDTO(empresa);
    }

    public void desassociarServico(Long empresaId, Long servicoId) {
        buscarEmpresa(empresaId);

        Servico servico = servicoRepositorio.findById(servicoId)
                .orElseThrow(() -> new ServicoNaoEncontradoException(servicoId));

        if (servico.getEmpresa() == null || !servico.getEmpresa().getId().equals(empresaId)) {
            throw new AssociacaoNaoEncontradaException(
                    "O serviço " + servicoId + " não está associado à empresa " + empresaId);
        }

        servico.setEmpresa(null);
        servicoRepositorio.save(servico);
    }

    private Empresa buscarEmpresa(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new EmpresaNaoEncontradaException(id));
    }
}