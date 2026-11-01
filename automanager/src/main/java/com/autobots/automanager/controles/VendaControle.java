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

import com.autobots.automanager.dto.request.VendaRequestDTO;
import com.autobots.automanager.dto.response.VendaResponseDTO;
import com.autobots.automanager.hateoas.AdicionadorLinkVenda;
import com.autobots.automanager.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaControle {

    @Autowired
    private VendaService service;

    @Autowired
    private AdicionadorLinkVenda adicionadorLink;

    @GetMapping
    public ResponseEntity<CollectionModel<VendaResponseDTO>> obterVendas(
            @RequestParam(required = false) Long empresaId,
            @RequestParam(required = false) Long clienteId) {

        List<VendaResponseDTO> lista = service.obterVendas(empresaId, clienteId);
        adicionadorLink.adicionarLink(lista);

        CollectionModel<VendaResponseDTO> colecao = CollectionModel.of(lista);
        colecao.add(linkTo(methodOn(VendaControle.class).obterVendas(empresaId, clienteId)).withSelfRel());
        colecao.add(linkTo(methodOn(VendaControle.class).cadastrarVenda(null)).withRel("cadastrar"));

        return ResponseEntity.ok(colecao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> obterVenda(@PathVariable Long id) {
        VendaResponseDTO resposta = service.obterVenda(id);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<VendaResponseDTO> cadastrarVenda(
            @Valid @RequestBody VendaRequestDTO dto) {

        VendaResponseDTO resposta = service.cadastrarVenda(dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity
                .created(linkTo(methodOn(VendaControle.class).obterVenda(resposta.getId())).toUri())
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> atualizarVenda(
            @PathVariable Long id,
            @Valid @RequestBody VendaRequestDTO dto) {

        VendaResponseDTO resposta = service.atualizarVenda(id, dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVenda(@PathVariable Long id) {
        service.excluirVenda(id);

        return ResponseEntity.noContent().build();
    }
}