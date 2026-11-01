package com.autobots.automanager.dto.response;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.TipoVeiculo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class VeiculoResponseDTO extends RepresentationModel<VeiculoResponseDTO> {

    private Long id;
    private TipoVeiculo tipo;
    private String modelo;
    private String placa;
    private UsuarioResumoDTO proprietario;
}