package com.autobots.automanager.dto.request;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank
    private String nome;

    private String nomeSocial;

    @NotEmpty
    private Set<PerfilUsuario> perfis;

    @Valid
    private List<TelefoneRequestDTO> telefones;

    @Valid
    private EnderecoRequestDTO endereco;

    @Valid
    private List<DocumentoRequestDTO> documentos;

    @Valid
    private List<EmailRequestDTO> emails;
}