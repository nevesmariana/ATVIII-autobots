package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.CredencialUsuarioSenhaRequestDTO;
import com.autobots.automanager.dto.response.CredencialUsuarioSenhaResponseDTO;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.excecoes.CredencialDuplicadaException;
import com.autobots.automanager.excecoes.CredencialNaoEncontradaException;
import com.autobots.automanager.mapper.CredencialUsuarioSenhaMapper;
import com.autobots.automanager.repositorios.CredencialUsuarioSenhaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class CredencialUsuarioSenhaService {

    @Autowired
    private CredencialUsuarioSenhaRepositorio repositorio;

    @Autowired
    private CredencialUsuarioSenhaMapper mapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<CredencialUsuarioSenhaResponseDTO> obterCredenciais() {
        List<CredencialUsuarioSenha> credenciais = repositorio.findAll();

        List<CredencialUsuarioSenhaResponseDTO> dtos = new ArrayList<>();

        for (CredencialUsuarioSenha credencial : credenciais) {
            dtos.add(mapper.paraResponseDTO(credencial));
        }

        return dtos;
    }

    public CredencialUsuarioSenhaResponseDTO obterCredencial(Long id) {
        CredencialUsuarioSenha credencial = repositorio.findById(id)
                .orElseThrow(() -> new CredencialNaoEncontradaException(id));

        return mapper.paraResponseDTO(credencial);
    }

    public CredencialUsuarioSenhaResponseDTO cadastrarCredencial(CredencialUsuarioSenhaRequestDTO dto) {
        if (repositorio.existsByNomeUsuario(dto.getNomeUsuario())) {
            throw new CredencialDuplicadaException(dto.getNomeUsuario());
        }

        CredencialUsuarioSenha credencial = mapper.paraEntidade(dto);

        credencial = repositorio.save(credencial);

        return mapper.paraResponseDTO(credencial);
    }

    public CredencialUsuarioSenhaResponseDTO atualizarCredencial(Long id, CredencialUsuarioSenhaRequestDTO dto) {
        CredencialUsuarioSenha credencial = repositorio.findById(id)
                .orElseThrow(() -> new CredencialNaoEncontradaException(id));

        if (repositorio.existsByNomeUsuarioAndIdNot(dto.getNomeUsuario(), id)) {
            throw new CredencialDuplicadaException(dto.getNomeUsuario());
        }

        mapper.atualizarEntidade(credencial, dto);

        credencial = repositorio.save(credencial);

        return mapper.paraResponseDTO(credencial);
    }

    public void excluirCredencial(Long id) {
        CredencialUsuarioSenha credencial = repositorio.findById(id)   // no outro: CredencialCodigoBarra
                .orElseThrow(() -> new CredencialNaoEncontradaException(id));

        usuarioRepositorio.findByCredenciais_Id(id)
                .ifPresent(usuario -> {
                    usuario.getCredenciais().removeIf(c -> c.getId().equals(id));
                    usuarioRepositorio.save(usuario);
                });

        repositorio.delete(credencial);
    }
}