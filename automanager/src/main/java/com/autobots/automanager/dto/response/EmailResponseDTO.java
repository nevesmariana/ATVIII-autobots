package com.autobots.automanager.dto.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmailResponseDTO extends RepresentationModel<EmailResponseDTO> {

    private Long id;
    private String endereco;
}