package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.DocumentoRequestDTO;
import com.autobots.automanager.dto.request.UsuarioRequestDTO;
import com.autobots.automanager.dto.response.UsuarioResponseDTO;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.excecoes.AssociacaoJaExistenteException;
import com.autobots.automanager.excecoes.AssociacaoNaoEncontradaException;
import com.autobots.automanager.excecoes.CredencialNaoEncontradaException;
import com.autobots.automanager.excecoes.DocumentoDuplicadoException;
import com.autobots.automanager.excecoes.ExclusaoNaoPermitidaException;
import com.autobots.automanager.excecoes.MercadoriaNaoEncontradaException;
import com.autobots.automanager.excecoes.UsuarioNaoEncontradoException;
import com.autobots.automanager.mapper.UsuarioMapper;
import com.autobots.automanager.repositorios.CredencialRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private UsuarioMapper mapper;

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private CredencialRepositorio credencialRepositorio;

    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    public List<UsuarioResponseDTO> obterUsuarios(PerfilUsuario perfil) {
        List<Usuario> usuarios;

        if (perfil == null) {
            usuarios = repositorio.findAll();
        } else {
            usuarios = repositorio.findByPerfil(perfil);
        }

        List<UsuarioResponseDTO> dtos = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            dtos.add(mapper.paraResponseDTO(usuario));
        }

        return dtos;
    }

    public UsuarioResponseDTO obterUsuario(Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return mapper.paraResponseDTO(usuario);
    }

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO dto) {
        validarDocumentos(dto, null);

        Usuario usuario = mapper.paraEntidade(dto);

        usuario = repositorio.save(usuario);

        return mapper.paraResponseDTO(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        validarDocumentos(dto, usuario);

        mapper.atualizarEntidade(usuario, dto);

        usuario = repositorio.save(usuario);

        return mapper.paraResponseDTO(usuario);
    }

    public void excluirUsuario(Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        if (vendaRepositorio.existsByCliente_Id(id) || vendaRepositorio.existsByFuncionario_Id(id)) {
            throw new ExclusaoNaoPermitidaException(
                    "O usuário " + id + " participa de vendas (como cliente ou funcionário) "
                    + "e não pode ser excluído");
        }

        if (veiculoRepositorio.existsByProprietario_Id(id)) {
            throw new ExclusaoNaoPermitidaException(
                    "O usuário " + id + " possui veículos cadastrados. "
                    + "Exclua ou transfira os veículos antes de excluir o usuário");
        }

        repositorio.delete(usuario);
    }

    public UsuarioResponseDTO associarCredencial(Long usuarioId, Long credencialId) {
        Usuario usuario = repositorio.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        Credencial credencial = credencialRepositorio.findById(credencialId)
                .orElseThrow(() -> new CredencialNaoEncontradaException(credencialId));

        if (repositorio.existsByCredenciais_Id(credencialId)) {
            throw new AssociacaoJaExistenteException(
                    "A credencial " + credencialId + " já está associada a um usuário");
        }

        usuario.getCredenciais().add(credencial);

        usuario = repositorio.save(usuario);

        return mapper.paraResponseDTO(usuario);
    }

    public void desassociarCredencial(Long usuarioId, Long credencialId) {
        Usuario usuario = repositorio.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        boolean removida = usuario.getCredenciais()
                .removeIf(credencial -> credencial.getId().equals(credencialId));

        if (!removida) {
            throw new AssociacaoNaoEncontradaException(
                    "A credencial " + credencialId + " não está associada ao usuário " + usuarioId);
        }

        repositorio.save(usuario);
    }

    public UsuarioResponseDTO associarMercadoria(Long usuarioId, Long mercadoriaId) {
        Usuario usuario = repositorio.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        Mercadoria mercadoria = mercadoriaRepositorio.findById(mercadoriaId)
                .orElseThrow(() -> new MercadoriaNaoEncontradaException(mercadoriaId));

        if (repositorio.existsByMercadorias_Id(mercadoriaId)) {
            throw new AssociacaoJaExistenteException(
                    "A mercadoria " + mercadoriaId + " já está associada a um usuário");
        }

        usuario.getMercadorias().add(mercadoria);

        usuario = repositorio.save(usuario);

        return mapper.paraResponseDTO(usuario);
    }

    public void desassociarMercadoria(Long usuarioId, Long mercadoriaId) {
        Usuario usuario = repositorio.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        boolean removida = usuario.getMercadorias()
                .removeIf(mercadoria -> mercadoria.getId().equals(mercadoriaId));

        if (!removida) {
            throw new AssociacaoNaoEncontradaException(
                    "A mercadoria " + mercadoriaId + " não está associada ao usuário " + usuarioId);
        }

        repositorio.save(usuario);
    }

    private void validarDocumentos(UsuarioRequestDTO dto, Usuario existente) {
        if (dto.getDocumentos() == null) {
            return;
        }

        Set<String> numerosAtuais = new HashSet<>();
        if (existente != null) {
            for (Documento atual : existente.getDocumentos()) {
                numerosAtuais.add(atual.getNumero());
            }
        }

        Set<String> numerosVistos = new HashSet<>();

        for (DocumentoRequestDTO documento : dto.getDocumentos()) {
            String numero = documento.getNumero();

            if (!numerosVistos.add(numero)) {
                throw new DocumentoDuplicadoException(numero);
            }

            boolean numeroNovo = !numerosAtuais.contains(numero);
            if (numeroNovo && documentoRepositorio.existsByNumero(numero)) {
                throw new DocumentoDuplicadoException(numero);
            }
        }
    }
}