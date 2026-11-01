package com.autobots.automanager.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EmailRequestDTO {

    @NotBlank
    @Email
    private String endereco;
}