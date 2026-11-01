package com.autobots.automanager.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.autobots.automanager.enumeracoes.TipoVeiculo;

import lombok.Data;

@Data
public class VeiculoRequestDTO {

    @NotNull
    private TipoVeiculo tipo;

    @NotBlank
    private String modelo;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{3}-?[0-9][A-Za-z0-9][0-9]{2}$",
            message = "placa deve estar no formato ABC-1234 ou ABC1D23")
    private String placa;

    @NotNull
    private Long proprietarioId;
}