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

import com.autobots.automanager.dto.request.VeiculoRequestDTO;
import com.autobots.automanager.dto.response.VeiculoResponseDTO;
import com.autobots.automanager.hateoas.AdicionadorLinkVeiculo;
import com.autobots.automanager.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoControle {

    @Autowired
    private VeiculoService service;

    @Autowired
    private AdicionadorLinkVeiculo adicionadorLink;

    @GetMapping
    public ResponseEntity<CollectionModel<VeiculoResponseDTO>> obterVeiculos(
            @RequestParam(required = false) Long proprietarioId) {

        List<VeiculoResponseDTO> lista = service.obterVeiculos(proprietarioId);
        adicionadorLink.adicionarLink(lista);

        CollectionModel<VeiculoResponseDTO> colecao = CollectionModel.of(lista);
        colecao.add(linkTo(methodOn(VeiculoControle.class).obterVeiculos(proprietarioId)).withSelfRel());
        colecao.add(linkTo(methodOn(VeiculoControle.class).cadastrarVeiculo(null)).withRel("cadastrar"));

        return ResponseEntity.ok(colecao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> obterVeiculo(@PathVariable Long id) {
        VeiculoResponseDTO resposta = service.obterVeiculo(id);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> cadastrarVeiculo(
            @Valid @RequestBody VeiculoRequestDTO dto) {

        VeiculoResponseDTO resposta = service.cadastrarVeiculo(dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity
                .created(linkTo(methodOn(VeiculoControle.class).obterVeiculo(resposta.getId())).toUri())
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> atualizarVeiculo(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoRequestDTO dto) {

        VeiculoResponseDTO resposta = service.atualizarVeiculo(id, dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVeiculo(@PathVariable Long id) {
        service.excluirVeiculo(id);

        return ResponseEntity.noContent().build();
    }
}