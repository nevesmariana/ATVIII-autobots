package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.VendaRequestDTO;
import com.autobots.automanager.dto.response.VendaResponseDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.excecoes.EmpresaNaoEncontradaException;
import com.autobots.automanager.excecoes.IdentificacaoDuplicadaException;
import com.autobots.automanager.excecoes.MercadoriaNaoEncontradaException;
import com.autobots.automanager.excecoes.ServicoNaoEncontradoException;
import com.autobots.automanager.excecoes.UsuarioNaoEncontradoException;
import com.autobots.automanager.excecoes.VeiculoNaoEncontradoException;
import com.autobots.automanager.excecoes.VendaInvalidaException;
import com.autobots.automanager.excecoes.VendaNaoEncontradaException;
import com.autobots.automanager.mapper.VendaMapper;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class VendaService {

    @Autowired
    private VendaRepositorio repositorio;

    @Autowired
    private VendaMapper mapper;

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;

    @Autowired
    private ServicoRepositorio servicoRepositorio;

    public List<VendaResponseDTO> obterVendas(Long empresaId, Long clienteId) {
        List<Venda> vendas;

        if (empresaId != null) {
            vendas = repositorio.findByEmpresa_Id(empresaId);
        } else {
            vendas = repositorio.findAll();
        }

        List<VendaResponseDTO> dtos = new ArrayList<>();

        for (Venda venda : vendas) {
            if (clienteId != null && !venda.getCliente().getId().equals(clienteId)) {
                continue;
            }

            dtos.add(mapper.paraResponseDTO(venda));
        }

        return dtos;
    }

    public VendaResponseDTO obterVenda(Long id) {
        Venda venda = repositorio.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException(id));

        return mapper.paraResponseDTO(venda);
    }

    public VendaResponseDTO cadastrarVenda(VendaRequestDTO dto) {
        if (repositorio.existsByIdentificacao(dto.getIdentificacao())) {
            throw new IdentificacaoDuplicadaException(dto.getIdentificacao());
        }

        Venda venda = mapper.paraEntidade(dto);

        preencherRelacoes(venda, dto);

        venda = repositorio.save(venda);

        return mapper.paraResponseDTO(venda);
    }

    public VendaResponseDTO atualizarVenda(Long id, VendaRequestDTO dto) {
        Venda venda = repositorio.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException(id));

        if (repositorio.existsByIdentificacaoAndIdNot(dto.getIdentificacao(), id)) {
            throw new IdentificacaoDuplicadaException(dto.getIdentificacao());
        }

        mapper.atualizarEntidade(venda, dto);

        preencherRelacoes(venda, dto);

        venda = repositorio.save(venda);

        return mapper.paraResponseDTO(venda);
    }

    public void excluirVenda(Long id) {
        Venda venda = repositorio.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException(id));

        repositorio.delete(venda);
    }

    private void preencherRelacoes(Venda venda, VendaRequestDTO dto) {
        boolean semMercadorias = dto.getMercadoriasIds() == null || dto.getMercadoriasIds().isEmpty();
        boolean semServicos = dto.getServicosIds() == null || dto.getServicosIds().isEmpty();

        if (semMercadorias && semServicos) {
            throw new VendaInvalidaException(
                    "A venda deve ter pelo menos uma mercadoria ou um serviço");
        }

        Empresa empresa = empresaRepositorio.findById(dto.getEmpresaId())
                .orElseThrow(() -> new EmpresaNaoEncontradaException(dto.getEmpresaId()));

        Usuario cliente = usuarioRepositorio.findById(dto.getClienteId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(dto.getClienteId()));

        if (!cliente.getPerfis().contains(PerfilUsuario.CLIENTE)) {
            throw new VendaInvalidaException(
                    "O usuário " + cliente.getId() + " não possui o perfil CLIENTE");
        }

        Usuario funcionario = usuarioRepositorio.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(dto.getFuncionarioId()));

        if (!funcionario.getPerfis().contains(PerfilUsuario.FUNCIONARIO)) {
            throw new VendaInvalidaException(
                    "O usuário " + funcionario.getId() + " não possui o perfil FUNCIONARIO");
        }

        if (!pertenceAEmpresa(funcionario.getEmpresa(), empresa.getId())) {
            throw new VendaInvalidaException(
                    "O funcionário " + funcionario.getId() + " não pertence à empresa " + empresa.getId());
        }

        Veiculo veiculo = veiculoRepositorio.findById(dto.getVeiculoId())
                .orElseThrow(() -> new VeiculoNaoEncontradoException(dto.getVeiculoId()));

        if (!veiculo.getProprietario().getId().equals(cliente.getId())) {
            throw new VendaInvalidaException(
                    "O veículo " + veiculo.getId() + " não pertence ao cliente " + cliente.getId());
        }

        Set<Mercadoria> mercadorias = new HashSet<>();
        if (dto.getMercadoriasIds() != null) {
            for (Long mercadoriaId : new LinkedHashSet<>(dto.getMercadoriasIds())) {
                Mercadoria mercadoria = mercadoriaRepositorio.findById(mercadoriaId)
                        .orElseThrow(() -> new MercadoriaNaoEncontradaException(mercadoriaId));

                if (!pertenceAEmpresa(mercadoria.getEmpresa(), empresa.getId())) {
                    throw new VendaInvalidaException(
                            "A mercadoria " + mercadoriaId + " não pertence à empresa " + empresa.getId());
                }

                mercadorias.add(mercadoria);
            }
        }

        Set<Servico> servicos = new HashSet<>();
        if (dto.getServicosIds() != null) {
            for (Long servicoId : new LinkedHashSet<>(dto.getServicosIds())) {
                Servico servico = servicoRepositorio.findById(servicoId)
                        .orElseThrow(() -> new ServicoNaoEncontradoException(servicoId));

                if (!pertenceAEmpresa(servico.getEmpresa(), empresa.getId())) {
                    throw new VendaInvalidaException(
                            "O serviço " + servicoId + " não pertence à empresa " + empresa.getId());
                }

                servicos.add(servico);
            }
        }

        venda.setEmpresa(empresa);
        venda.setCliente(cliente);
        venda.setFuncionario(funcionario);
        venda.setVeiculo(veiculo);

        venda.getMercadorias().clear();
        venda.getMercadorias().addAll(mercadorias);

        venda.getServicos().clear();
        venda.getServicos().addAll(servicos);
    }

    private boolean pertenceAEmpresa(Empresa dona, Long empresaId) {
        return dona != null && dona.getId().equals(empresaId);
    }
}