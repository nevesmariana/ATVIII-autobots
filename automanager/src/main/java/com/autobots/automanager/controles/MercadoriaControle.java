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

import com.autobots.automanager.dto.request.MercadoriaRequestDTO;
import com.autobots.automanager.dto.response.MercadoriaResponseDTO;
import com.autobots.automanager.hateoas.AdicionadorLinkMercadoria;
import com.autobots.automanager.service.MercadoriaService;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaControle {

    @Autowired
    private MercadoriaService service;

    @Autowired
    private AdicionadorLinkMercadoria adicionadorLink;

    @GetMapping
    public ResponseEntity<CollectionModel<MercadoriaResponseDTO>> obterMercadorias() {
        List<MercadoriaResponseDTO> lista = service.obterMercadorias();
        adicionadorLink.adicionarLink(lista);

        CollectionModel<MercadoriaResponseDTO> colecao = CollectionModel.of(lista);
        colecao.add(linkTo(methodOn(MercadoriaControle.class).obterMercadorias()).withSelfRel());
        colecao.add(linkTo(methodOn(MercadoriaControle.class).cadastrarMercadoria(null)).withRel("cadastrar"));

        return ResponseEntity.ok(colecao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MercadoriaResponseDTO> obterMercadoria(@PathVariable Long id) {
        MercadoriaResponseDTO resposta = service.obterMercadoria(id);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<MercadoriaResponseDTO> cadastrarMercadoria(
            @Valid @RequestBody MercadoriaRequestDTO dto) {
        MercadoriaResponseDTO resposta = service.cadastrarMercadoria(dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity
                .created(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(resposta.getId())).toUri())
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MercadoriaResponseDTO> atualizarMercadoria(
            @PathVariable Long id,
            @Valid @RequestBody MercadoriaRequestDTO dto) {
        MercadoriaResponseDTO resposta = service.atualizarMercadoria(id, dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMercadoria(@PathVariable Long id) {
        service.excluirMercadoria(id);
        return ResponseEntity.noContent().build();
    }
}