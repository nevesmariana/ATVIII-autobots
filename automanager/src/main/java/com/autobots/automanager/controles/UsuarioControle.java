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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.dto.request.UsuarioRequestDTO;
import com.autobots.automanager.dto.response.UsuarioResponseDTO;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.hateoas.AdicionadorLinkUsuario;
import com.autobots.automanager.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {

    @Autowired
    private UsuarioService service;

    @Autowired
    private AdicionadorLinkUsuario adicionadorLink;

    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioResponseDTO>> obterUsuarios(
            @RequestParam(required = false) PerfilUsuario perfil) {

        List<UsuarioResponseDTO> lista = service.obterUsuarios(perfil);
        adicionadorLink.adicionarLink(lista);

        CollectionModel<UsuarioResponseDTO> colecao = CollectionModel.of(lista);
        colecao.add(linkTo(methodOn(UsuarioControle.class).obterUsuarios(perfil)).withSelfRel());
        colecao.add(linkTo(methodOn(UsuarioControle.class).cadastrarUsuario(null)).withRel("cadastrar"));
        colecao.add(linkTo(methodOn(UsuarioControle.class).obterUsuarios(PerfilUsuario.CLIENTE)).withRel("clientes"));
        colecao.add(linkTo(methodOn(UsuarioControle.class).obterUsuarios(PerfilUsuario.FORNECEDOR)).withRel("fornecedores"));
        colecao.add(linkTo(methodOn(UsuarioControle.class).obterUsuarios(PerfilUsuario.FUNCIONARIO)).withRel("funcionarios"));

        return ResponseEntity.ok(colecao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obterUsuario(@PathVariable Long id) {
        UsuarioResponseDTO resposta = service.obterUsuario(id);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(
            @Valid @RequestBody UsuarioRequestDTO dto) {

        UsuarioResponseDTO resposta = service.cadastrarUsuario(dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControle.class).obterUsuario(resposta.getId())).toUri())
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {

        UsuarioResponseDTO resposta = service.atualizarUsuario(id, dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        service.excluirUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/credenciais/{credencialId}")
    public ResponseEntity<UsuarioResponseDTO> associarCredencial(
            @PathVariable Long id,
            @PathVariable Long credencialId) {

        UsuarioResponseDTO resposta = service.associarCredencial(id, credencialId);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}/credenciais/{credencialId}")
    public ResponseEntity<Void> desassociarCredencial(
            @PathVariable Long id,
            @PathVariable Long credencialId) {

        service.desassociarCredencial(id, credencialId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/mercadorias/{mercadoriaId}")
    public ResponseEntity<UsuarioResponseDTO> associarMercadoria(
            @PathVariable Long id,
            @PathVariable Long mercadoriaId) {

        UsuarioResponseDTO resposta = service.associarMercadoria(id, mercadoriaId);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}/mercadorias/{mercadoriaId}")
    public ResponseEntity<Void> desassociarMercadoria(
            @PathVariable Long id,
            @PathVariable Long mercadoriaId) {

        service.desassociarMercadoria(id, mercadoriaId);

        return ResponseEntity.noContent().build();
    }
}