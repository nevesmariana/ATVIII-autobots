package com.autobots.automanager.dto.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServicoResponseDTO extends RepresentationModel<ServicoResponseDTO> {

    private Long id;
    private String nome;
    private double valor;
    private String descricao;
}