package com.autobots.automanager.dto.response;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CredencialCodigoBarraResponseDTO extends RepresentationModel<CredencialCodigoBarraResponseDTO> {

    private Long id;
    private long codigo;
    private Date criacao;
    private Date ultimoAcesso;
    private boolean inativo;
}