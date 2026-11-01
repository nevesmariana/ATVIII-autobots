package com.autobots.automanager.dto.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.autobots.automanager.enumeracoes.TipoDocumento;

import lombok.Data;

@Data
public class DocumentoRequestDTO {

    @NotNull
    private TipoDocumento tipo;

    @NotNull
    private Date dataEmissao;

    @NotBlank
    private String numero;
}