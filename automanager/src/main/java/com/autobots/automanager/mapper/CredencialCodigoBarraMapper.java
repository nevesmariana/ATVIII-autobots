package com.autobots.automanager.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.request.CredencialCodigoBarraRequestDTO;
import com.autobots.automanager.dto.response.CredencialCodigoBarraResponseDTO;
import com.autobots.automanager.entidades.CredencialCodigoBarra;

@Component
public class CredencialCodigoBarraMapper {

    public CredencialCodigoBarra paraEntidade(CredencialCodigoBarraRequestDTO dto) {
        CredencialCodigoBarra credencial = new CredencialCodigoBarra();

        credencial.setCodigo(dto.getCodigo());
        credencial.setCriacao(new Date());
        credencial.setInativo(Boolean.TRUE.equals(dto.getInativo()));

        return credencial;
    }

    public CredencialCodigoBarraResponseDTO paraResponseDTO(CredencialCodigoBarra credencial) {
        CredencialCodigoBarraResponseDTO dto = new CredencialCodigoBarraResponseDTO();

        dto.setId(credencial.getId());
        dto.setCodigo(credencial.getCodigo());
        dto.setCriacao(credencial.getCriacao());
        dto.setUltimoAcesso(credencial.getUltimoAcesso());
        dto.setInativo(credencial.isInativo());

        return dto;
    }

    public void atualizarEntidade(CredencialCodigoBarra credencial, CredencialCodigoBarraRequestDTO dto) {
        credencial.setCodigo(dto.getCodigo());

        if (dto.getInativo() != null) {
            credencial.setInativo(dto.getInativo());
        }
    }
}