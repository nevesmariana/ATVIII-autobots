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

import com.autobots.automanager.dto.request.CredencialCodigoBarraRequestDTO;
import com.autobots.automanager.dto.response.CredencialCodigoBarraResponseDTO;
import com.autobots.automanager.hateoas.AdicionadorLinkCredencialCodigoBarra;
import com.autobots.automanager.service.CredencialCodigoBarraService;

@RestController
@RequestMapping("/credenciais/codigo-barra")
public class CredencialCodigoBarraControle {

    @Autowired
    private CredencialCodigoBarraService service;

    @Autowired
    private AdicionadorLinkCredencialCodigoBarra adicionadorLink;

    @GetMapping
    public ResponseEntity<CollectionModel<CredencialCodigoBarraResponseDTO>> obterCredenciais() {
        List<CredencialCodigoBarraResponseDTO> lista = service.obterCredenciais();
        adicionadorLink.adicionarLink(lista);

        CollectionModel<CredencialCodigoBarraResponseDTO> colecao = CollectionModel.of(lista);
        colecao.add(linkTo(methodOn(CredencialCodigoBarraControle.class).obterCredenciais()).withSelfRel());
        colecao.add(linkTo(methodOn(CredencialCodigoBarraControle.class).cadastrarCredencial(null)).withRel("cadastrar"));

        return ResponseEntity.ok(colecao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CredencialCodigoBarraResponseDTO> obterCredencial(@PathVariable Long id) {
        CredencialCodigoBarraResponseDTO resposta = service.obterCredencial(id);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<CredencialCodigoBarraResponseDTO> cadastrarCredencial(
            @Valid @RequestBody CredencialCodigoBarraRequestDTO dto) {

        CredencialCodigoBarraResponseDTO resposta = service.cadastrarCredencial(dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity
                .created(linkTo(methodOn(CredencialCodigoBarraControle.class).obterCredencial(resposta.getId())).toUri())
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CredencialCodigoBarraResponseDTO> atualizarCredencial(
            @PathVariable Long id,
            @Valid @RequestBody CredencialCodigoBarraRequestDTO dto) {

        CredencialCodigoBarraResponseDTO resposta = service.atualizarCredencial(id, dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCredencial(@PathVariable Long id) {
        service.excluirCredencial(id);

        return ResponseEntity.noContent().build();
    }
}