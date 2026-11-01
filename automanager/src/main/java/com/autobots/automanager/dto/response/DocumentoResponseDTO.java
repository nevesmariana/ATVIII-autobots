package com.autobots.automanager.dto.response;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.TipoDocumento;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentoResponseDTO extends RepresentationModel<DocumentoResponseDTO> {

    private Long id;
    private TipoDocumento tipo;
    private Date dataEmissao;
    private String numero;
}