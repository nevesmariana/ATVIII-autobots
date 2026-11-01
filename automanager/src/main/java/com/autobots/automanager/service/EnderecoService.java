package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.EnderecoRequestDTO;
import com.autobots.automanager.dto.response.EnderecoResponseDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.excecoes.EnderecoNaoEncontradoException;
import com.autobots.automanager.mapper.EnderecoMapper;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepositorio repositorio;

    @Autowired
    private EnderecoMapper mapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    public EnderecoResponseDTO cadastrarEndereco(
            EnderecoRequestDTO dto) {
        Endereco endereco = mapper.paraEntidade(dto);

        endereco = repositorio.save(endereco);

        return mapper.paraResponseDTO(endereco);
    }

    public List<EnderecoResponseDTO> obterEnderecos() {
        List<Endereco> enderecos = repositorio.findAll();

        List<EnderecoResponseDTO> dtos = new ArrayList<>();

        for (Endereco endereco : enderecos) {
            dtos.add(mapper.paraResponseDTO(endereco));
        }

        return dtos;
    }

    public EnderecoResponseDTO obterEndereco(Long id) {
        Endereco endereco = repositorio.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(id));

        return mapper.paraResponseDTO(endereco);
    }

    public EnderecoResponseDTO atualizarEndereco(
            Long id,
            EnderecoRequestDTO dto) {
        Endereco endereco = repositorio.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(id));

        mapper.atualizarEntidade(endereco, dto);

        endereco = repositorio.save(endereco);

        return mapper.paraResponseDTO(endereco);
    }

    public void excluirEndereco(Long id) {
        Endereco endereco = repositorio.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(id));

        Optional<Usuario> usuario = usuarioRepositorio.findByEndereco_Id(id);
        if (usuario.isPresent()) {
            usuario.get().setEndereco(null);
            usuarioRepositorio.save(usuario.get());
            return;
        }

        Optional<Empresa> empresa = empresaRepositorio.findByEndereco_Id(id);
        if (empresa.isPresent()) {
            empresa.get().setEndereco(null);
            empresaRepositorio.save(empresa.get());
            return;
        }

        repositorio.delete(endereco);
    }
}