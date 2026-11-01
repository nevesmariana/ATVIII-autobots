package com.autobots.automanager.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.request.MercadoriaRequestDTO;
import com.autobots.automanager.dto.response.MercadoriaResponseDTO;
import com.autobots.automanager.entidades.Mercadoria;

@Component
public class MercadoriaMapper {

    public Mercadoria paraEntidade(MercadoriaRequestDTO dto) {
        Mercadoria mercadoria = new Mercadoria();

        mercadoria.setNome(dto.getNome());
        mercadoria.setFabricacao(dto.getFabricacao());
        mercadoria.setValidade(dto.getValidade());
        mercadoria.setQuantidade(dto.getQuantidade());
        mercadoria.setValor(dto.getValor());
        mercadoria.setDescricao(dto.getDescricao());
        mercadoria.setCadastro(new Date());

        return mercadoria;
    }

    public MercadoriaResponseDTO paraResponseDTO(Mercadoria mercadoria) {
        MercadoriaResponseDTO dto = new MercadoriaResponseDTO();

        dto.setId(mercadoria.getId());
        dto.setNome(mercadoria.getNome());
        dto.setFabricacao(mercadoria.getFabricacao());
        dto.setValidade(mercadoria.getValidade());
        dto.setCadastro(mercadoria.getCadastro());
        dto.setQuantidade(mercadoria.getQuantidade());
        dto.setValor(mercadoria.getValor());
        dto.setDescricao(mercadoria.getDescricao());

        return dto;
    }

    public void atualizarEntidade(Mercadoria mercadoria, MercadoriaRequestDTO dto) {
        mercadoria.setNome(dto.getNome());
        mercadoria.setFabricacao(dto.getFabricacao());
        mercadoria.setValidade(dto.getValidade());
        mercadoria.setQuantidade(dto.getQuantidade());
        mercadoria.setValor(dto.getValor());
        mercadoria.setDescricao(dto.getDescricao());
    }
}