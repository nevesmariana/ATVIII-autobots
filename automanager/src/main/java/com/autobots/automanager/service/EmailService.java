package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.EmailRequestDTO;
import com.autobots.automanager.dto.response.EmailResponseDTO;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.excecoes.EmailNaoEncontradoException;
import com.autobots.automanager.mapper.EmailMapper;
import com.autobots.automanager.repositorios.EmailRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class EmailService {

    @Autowired
    private EmailRepositorio repositorio;

    @Autowired
    private EmailMapper mapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<EmailResponseDTO> obterEmails() {
        List<Email> emails = repositorio.findAll();

        List<EmailResponseDTO> dtos = new ArrayList<>();

        for (Email email : emails) {
            dtos.add(mapper.paraResponseDTO(email));
        }

        return dtos;
    }

    public EmailResponseDTO obterEmail(Long id) {
        Email email = repositorio.findById(id)
                .orElseThrow(() -> new EmailNaoEncontradoException(id));

        return mapper.paraResponseDTO(email);
    }

    public EmailResponseDTO cadastrarEmail(EmailRequestDTO dto) {
        Email email = mapper.paraEntidade(dto);

        email = repositorio.save(email);

        return mapper.paraResponseDTO(email);
    }

    public EmailResponseDTO atualizarEmail(Long id, EmailRequestDTO dto) {
        Email email = repositorio.findById(id)
                .orElseThrow(() -> new EmailNaoEncontradoException(id));

        mapper.atualizarEntidade(email, dto);

        email = repositorio.save(email);

        return mapper.paraResponseDTO(email);
    }

    public void excluirEmail(Long id) {
        Email email = repositorio.findById(id)
                .orElseThrow(() -> new EmailNaoEncontradoException(id));

        usuarioRepositorio.findByEmails_Id(id)
                .ifPresentOrElse(
                        usuario -> {
                            usuario.getEmails().removeIf(e -> e.getId().equals(id));
                            usuarioRepositorio.save(usuario);
                        },
                        () -> repositorio.delete(email));
    }
}