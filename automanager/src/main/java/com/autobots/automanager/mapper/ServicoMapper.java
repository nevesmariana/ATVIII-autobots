package com.autobots.automanager.mapper;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.request.ServicoRequestDTO;
import com.autobots.automanager.dto.response.ServicoResponseDTO;
import com.autobots.automanager.entidades.Servico;

@Component
public class ServicoMapper {

    public Servico paraEntidade(ServicoRequestDTO dto) {
        Servico servico = new Servico();

        servico.setNome(dto.getNome());
        servico.setValor(dto.getValor());
        servico.setDescricao(dto.getDescricao());

        return servico;
    }

    public ServicoResponseDTO paraResponseDTO(Servico servico) {
        ServicoResponseDTO dto = new ServicoResponseDTO();

        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setValor(servico.getValor());
        dto.setDescricao(servico.getDescricao());

        return dto;
    }

    public void atualizarEntidade(Servico servico, ServicoRequestDTO dto) {
        servico.setNome(dto.getNome());
        servico.setValor(dto.getValor());
        servico.setDescricao(dto.getDescricao());
    }
}