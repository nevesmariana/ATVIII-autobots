package com.autobots.automanager.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VendaRequestDTO {

    @NotBlank
    private String identificacao;

    @NotNull
    private Long empresaId;

    @NotNull
    private Long clienteId;

    @NotNull
    private Long funcionarioId;

    @NotNull
    private Long veiculoId;

    private List<Long> mercadoriasIds;

    private List<Long> servicosIds;
}