package com.autobots.automanager.dto.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServicoResumoDTO extends RepresentationModel<ServicoResumoDTO> {

    private Long id;
    private String nome;
}