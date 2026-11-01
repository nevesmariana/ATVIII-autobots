package com.autobots.automanager.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class CredencialCodigoBarraRequestDTO {

    @NotNull
    @Positive
    private Long codigo;

    private Boolean inativo;
}