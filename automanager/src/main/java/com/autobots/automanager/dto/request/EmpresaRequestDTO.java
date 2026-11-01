package com.autobots.automanager.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EmpresaRequestDTO {

    @NotBlank
    private String razaoSocial;

    private String nomeFantasia;

    @Valid
    private List<TelefoneRequestDTO> telefones;

    @Valid
    private EnderecoRequestDTO endereco;
}