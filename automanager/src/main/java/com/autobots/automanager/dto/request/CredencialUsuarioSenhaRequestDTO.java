package com.autobots.automanager.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CredencialUsuarioSenhaRequestDTO {

    @NotBlank
    private String nomeUsuario;

    @NotBlank
    @Size(min = 6)
    private String senha;

    private Boolean inativo;
}