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

import com.autobots.automanager.dto.request.CredencialUsuarioSenhaRequestDTO;
import com.autobots.automanager.dto.response.CredencialUsuarioSenhaResponseDTO;
import com.autobots.automanager.hateoas.AdicionadorLinkCredencialUsuarioSenha;
import com.autobots.automanager.service.CredencialUsuarioSenhaService;

@RestController
@RequestMapping("/credenciais/usuario-senha")
public class CredencialUsuarioSenhaControle {

    @Autowired
    private CredencialUsuarioSenhaService service;

    @Autowired
    private AdicionadorLinkCredencialUsuarioSenha adicionadorLink;

    @GetMapping
    public ResponseEntity<CollectionModel<CredencialUsuarioSenhaResponseDTO>> obterCredenciais() {
        List<CredencialUsuarioSenhaResponseDTO> lista = service.obterCredenciais();
        adicionadorLink.adicionarLink(lista);

        CollectionModel<CredencialUsuarioSenhaResponseDTO> colecao = CollectionModel.of(lista);
        colecao.add(linkTo(methodOn(CredencialUsuarioSenhaControle.class).obterCredenciais()).withSelfRel());
        colecao.add(linkTo(methodOn(CredencialUsuarioSenhaControle.class).cadastrarCredencial(null)).withRel("cadastrar"));

        return ResponseEntity.ok(colecao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CredencialUsuarioSenhaResponseDTO> obterCredencial(@PathVariable Long id) {
        CredencialUsuarioSenhaResponseDTO resposta = service.obterCredencial(id);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<CredencialUsuarioSenhaResponseDTO> cadastrarCredencial(
            @Valid @RequestBody CredencialUsuarioSenhaRequestDTO dto) {

        CredencialUsuarioSenhaResponseDTO resposta = service.cadastrarCredencial(dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity
                .created(linkTo(methodOn(CredencialUsuarioSenhaControle.class).obterCredencial(resposta.getId())).toUri())
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CredencialUsuarioSenhaResponseDTO> atualizarCredencial(
            @PathVariable Long id,
            @Valid @RequestBody CredencialUsuarioSenhaRequestDTO dto) {

        CredencialUsuarioSenhaResponseDTO resposta = service.atualizarCredencial(id, dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCredencial(@PathVariable Long id) {
        service.excluirCredencial(id);

        return ResponseEntity.noContent().build();
    }
}