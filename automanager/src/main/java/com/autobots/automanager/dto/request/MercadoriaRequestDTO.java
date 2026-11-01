package com.autobots.automanager.dto.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class MercadoriaRequestDTO {

    @NotBlank
    private String nome;

    @NotNull
    private Date fabricacao;

    @NotNull
    private Date validade;

    @NotNull
    @PositiveOrZero
    private Long quantidade;

    @NotNull
    @Positive
    private Double valor;

    private String descricao;
}