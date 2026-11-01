package com.autobots.automanager.mapper;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.request.EmailRequestDTO;
import com.autobots.automanager.dto.response.EmailResponseDTO;
import com.autobots.automanager.entidades.Email;

@Component
public class EmailMapper {

    public Email paraEntidade(EmailRequestDTO dto) {
        Email email = new Email();

        email.setEndereco(dto.getEndereco());

        return email;
    }

    public EmailResponseDTO paraResponseDTO(Email email) {
        EmailResponseDTO dto = new EmailResponseDTO();

        dto.setId(email.getId());
        dto.setEndereco(email.getEndereco());

        return dto;
    }

    public void atualizarEntidade(Email email, EmailRequestDTO dto) {
        email.setEndereco(dto.getEndereco());
    }
}