package com.autobots.automanager.controles;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.dto.request.EmailRequestDTO;
import com.autobots.automanager.dto.response.EmailResponseDTO;
import com.autobots.automanager.hateoas.AdicionadorLinkEmail;
import com.autobots.automanager.service.EmailService;

@RestController
@RequestMapping("/emails")
public class EmailControle {

    @Autowired
    private EmailService service;

    @Autowired
    private AdicionadorLinkEmail adicionadorLink;

    @GetMapping
    public ResponseEntity<CollectionModel<EmailResponseDTO>> obterEmails() {
        List<EmailResponseDTO> lista = service.obterEmails();
        adicionadorLink.adicionarLink(lista);

        CollectionModel<EmailResponseDTO> colecao = CollectionModel.of(lista);
        colecao.add(linkTo(methodOn(EmailControle.class).obterEmails()).withSelfRel());
        colecao.add(linkTo(methodOn(EmailControle.class).cadastrarEmail(null)).withRel("cadastrar"));

        return ResponseEntity.ok(colecao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailResponseDTO> obterEmail(@PathVariable Long id) {
        EmailResponseDTO resposta = service.obterEmail(id);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<EmailResponseDTO> cadastrarEmail(
            @Valid @RequestBody EmailRequestDTO dto) {

        EmailResponseDTO resposta = service.cadastrarEmail(dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity
                .created(linkTo(methodOn(EmailControle.class).obterEmail(resposta.getId())).toUri())
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailResponseDTO> atualizarEmail(
            @PathVariable Long id,
            @Valid @RequestBody EmailRequestDTO dto) {

        EmailResponseDTO resposta = service.atualizarEmail(id, dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEmail(@PathVariable Long id) {
        service.excluirEmail(id);

        return ResponseEntity.noContent().build();
    }
}