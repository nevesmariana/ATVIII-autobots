package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.CredencialCodigoBarraRequestDTO;
import com.autobots.automanager.dto.response.CredencialCodigoBarraResponseDTO;
import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.excecoes.CredencialDuplicadaException;
import com.autobots.automanager.excecoes.CredencialNaoEncontradaException;
import com.autobots.automanager.mapper.CredencialCodigoBarraMapper;
import com.autobots.automanager.repositorios.CredencialCodigoBarraRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class CredencialCodigoBarraService {

    @Autowired
    private CredencialCodigoBarraRepositorio repositorio;

    @Autowired
    private CredencialCodigoBarraMapper mapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<CredencialCodigoBarraResponseDTO> obterCredenciais() {
        List<CredencialCodigoBarra> credenciais = repositorio.findAll();

        List<CredencialCodigoBarraResponseDTO> dtos = new ArrayList<>();

        for (CredencialCodigoBarra credencial : credenciais) {
            dtos.add(mapper.paraResponseDTO(credencial));
        }

        return dtos;
    }

    public CredencialCodigoBarraResponseDTO obterCredencial(Long id) {
        CredencialCodigoBarra credencial = repositorio.findById(id)
                .orElseThrow(() -> new CredencialNaoEncontradaException(id));

        return mapper.paraResponseDTO(credencial);
    }

    public CredencialCodigoBarraResponseDTO cadastrarCredencial(CredencialCodigoBarraRequestDTO dto) {
        if (repositorio.existsByCodigo(dto.getCodigo())) {
            throw new CredencialDuplicadaException(String.valueOf(dto.getCodigo()));
        }

        CredencialCodigoBarra credencial = mapper.paraEntidade(dto);

        credencial = repositorio.save(credencial);

        return mapper.paraResponseDTO(credencial);
    }

    public CredencialCodigoBarraResponseDTO atualizarCredencial(Long id, CredencialCodigoBarraRequestDTO dto) {
        CredencialCodigoBarra credencial = repositorio.findById(id)
                .orElseThrow(() -> new CredencialNaoEncontradaException(id));

        if (repositorio.existsByCodigoAndIdNot(dto.getCodigo(), id)) {
            throw new CredencialDuplicadaException(String.valueOf(dto.getCodigo()));
        }

        mapper.atualizarEntidade(credencial, dto);

        credencial = repositorio.save(credencial);

        return mapper.paraResponseDTO(credencial);
    }

    public void excluirCredencial(Long id) {
        CredencialCodigoBarra credencial = repositorio.findById(id)
                .orElseThrow(() -> new CredencialNaoEncontradaException(id));

        usuarioRepositorio.findByCredenciais_Id(id)
                .ifPresent(usuario -> {
                    usuario.getCredenciais().removeIf(c -> c.getId().equals(id));
                    usuarioRepositorio.save(usuario);
                });

        repositorio.delete(credencial);
    }
}