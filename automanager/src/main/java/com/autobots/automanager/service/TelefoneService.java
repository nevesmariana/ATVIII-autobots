package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.TelefoneRequestDTO;
import com.autobots.automanager.dto.response.TelefoneResponseDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.excecoes.TelefoneNaoEncontradoException;
import com.autobots.automanager.mapper.TelefoneMapper;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepositorio repositorio;

    @Autowired
    private TelefoneMapper mapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    public TelefoneResponseDTO cadastrarTelefone(
            TelefoneRequestDTO dto) {
        Telefone telefone = mapper.paraEntidade(dto);

        telefone = repositorio.save(telefone);

        return mapper.paraResponseDTO(telefone);
    }

    public List<TelefoneResponseDTO> obterTelefones() {
        List<Telefone> telefones = repositorio.findAll();

        List<TelefoneResponseDTO> dtos = new ArrayList<>();

        for (Telefone telefone : telefones) {
            dtos.add(mapper.paraResponseDTO(telefone));
        }

        return dtos;
    }

    public TelefoneResponseDTO obterTelefone(Long id) {
        Telefone telefone = repositorio.findById(id)
                .orElseThrow(() -> new TelefoneNaoEncontradoException(id));

        return mapper.paraResponseDTO(telefone);
    }

    public TelefoneResponseDTO atualizarTelefone(
            Long id,
            TelefoneRequestDTO dto) {
        Telefone telefone = repositorio.findById(id)
                .orElseThrow(() -> new TelefoneNaoEncontradoException(id));

        mapper.atualizarEntidade(telefone, dto);

        telefone = repositorio.save(telefone);

        return mapper.paraResponseDTO(telefone);
    }

    public void excluirTelefone(Long id) {
        Telefone telefone = repositorio.findById(id)
                .orElseThrow(() -> new TelefoneNaoEncontradoException(id));

        Optional<Usuario> usuario = usuarioRepositorio.findByTelefones_Id(id);
        if (usuario.isPresent()) {
            usuario.get().getTelefones().removeIf(t -> t.getId().equals(id));
            usuarioRepositorio.save(usuario.get());
            return;
        }

        Optional<Empresa> empresa = empresaRepositorio.findByTelefones_Id(id);
        if (empresa.isPresent()) {
            empresa.get().getTelefones().removeIf(t -> t.getId().equals(id));
            empresaRepositorio.save(empresa.get());
            return;
        }

        repositorio.delete(telefone);
    }
}