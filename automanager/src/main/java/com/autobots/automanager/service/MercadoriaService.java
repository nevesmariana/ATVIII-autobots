package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.MercadoriaRequestDTO;
import com.autobots.automanager.dto.response.MercadoriaResponseDTO;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.excecoes.DatasMercadoriaInvalidasException;
import com.autobots.automanager.excecoes.MercadoriaNaoEncontradaException;
import com.autobots.automanager.mapper.MercadoriaMapper;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.excecoes.ExclusaoNaoPermitidaException;

@Service
public class MercadoriaService {

    @Autowired
    private MercadoriaRepositorio repositorio;

    @Autowired
    private MercadoriaMapper mapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    public List<MercadoriaResponseDTO> obterMercadorias() {
        List<Mercadoria> mercadorias = repositorio.findAll();

        List<MercadoriaResponseDTO> dtos = new ArrayList<>();

        for (Mercadoria mercadoria : mercadorias) {
            dtos.add(mapper.paraResponseDTO(mercadoria));
        }
        return dtos;
    }

    public MercadoriaResponseDTO obterMercadoria(Long id) {
        Mercadoria mercadoria = repositorio.findById(id)
                .orElseThrow(() -> new MercadoriaNaoEncontradaException(id));
        return mapper.paraResponseDTO(mercadoria);
    }

    public MercadoriaResponseDTO cadastrarMercadoria(MercadoriaRequestDTO dto) {
        validarDatas(dto);
        Mercadoria mercadoria = mapper.paraEntidade(dto);
        mercadoria = repositorio.save(mercadoria);
        return mapper.paraResponseDTO(mercadoria);
    }

    public MercadoriaResponseDTO atualizarMercadoria(Long id, MercadoriaRequestDTO dto) {
        Mercadoria mercadoria = repositorio.findById(id)
                .orElseThrow(() -> new MercadoriaNaoEncontradaException(id));
        validarDatas(dto);
        mapper.atualizarEntidade(mercadoria, dto);
        mercadoria = repositorio.save(mercadoria);
        return mapper.paraResponseDTO(mercadoria);
    }

    public void excluirMercadoria(Long id) {
        Mercadoria mercadoria = repositorio.findById(id)
                .orElseThrow(() -> new MercadoriaNaoEncontradaException(id));

        if (vendaRepositorio.existsByMercadorias_Id(id)) {
            throw new ExclusaoNaoPermitidaException(
                    "A mercadoria " + id + " está em vendas e não pode ser excluída");
        }

        usuarioRepositorio.findByMercadorias_Id(id)
                .ifPresent(usuario -> {
                    usuario.getMercadorias().removeIf(m -> m.getId().equals(id));
                    usuarioRepositorio.save(usuario);
                });

        repositorio.delete(mercadoria);
    }

    private void validarDatas(MercadoriaRequestDTO dto) {
        if (!dto.getValidade().after(dto.getFabricacao())) {
            throw new DatasMercadoriaInvalidasException();
        }
    }
}