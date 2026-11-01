package com.autobots.automanager.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class ServicoRequestDTO {

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private Double valor;

    private String descricao;
}