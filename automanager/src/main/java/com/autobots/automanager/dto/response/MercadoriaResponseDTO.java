package com.autobots.automanager.dto.response;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MercadoriaResponseDTO extends RepresentationModel<MercadoriaResponseDTO> {

    private Long id;
    private String nome;
    private Date fabricacao;
    private Date validade;
    private Date cadastro;
    private long quantidade;
    private double valor;
    private String descricao;
}