package com.autobots.automanager.dto.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class VeiculoResumoDTO extends RepresentationModel<VeiculoResumoDTO> {

    private Long id;
    private String modelo;
    private String placa;
}