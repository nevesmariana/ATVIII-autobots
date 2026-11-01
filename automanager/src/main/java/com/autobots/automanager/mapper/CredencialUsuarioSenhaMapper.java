package com.autobots.automanager.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.request.CredencialUsuarioSenhaRequestDTO;
import com.autobots.automanager.dto.response.CredencialUsuarioSenhaResponseDTO;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;

@Component
public class CredencialUsuarioSenhaMapper {

    public CredencialUsuarioSenha paraEntidade(CredencialUsuarioSenhaRequestDTO dto) {
        CredencialUsuarioSenha credencial = new CredencialUsuarioSenha();

        credencial.setNomeUsuario(dto.getNomeUsuario());
        credencial.setSenha(dto.getSenha());
        credencial.setCriacao(new Date());
        credencial.setInativo(Boolean.TRUE.equals(dto.getInativo()));

        return credencial;
    }

    public CredencialUsuarioSenhaResponseDTO paraResponseDTO(CredencialUsuarioSenha credencial) {
        CredencialUsuarioSenhaResponseDTO dto = new CredencialUsuarioSenhaResponseDTO();

        dto.setId(credencial.getId());
        dto.setNomeUsuario(credencial.getNomeUsuario());
        dto.setCriacao(credencial.getCriacao());
        dto.setUltimoAcesso(credencial.getUltimoAcesso());
        dto.setInativo(credencial.isInativo());

        return dto;
    }

    public void atualizarEntidade(CredencialUsuarioSenha credencial, CredencialUsuarioSenhaRequestDTO dto) {
        credencial.setNomeUsuario(dto.getNomeUsuario());
        credencial.setSenha(dto.getSenha());

        if (dto.getInativo() != null) {
            credencial.setInativo(dto.getInativo());
        }
    }
}