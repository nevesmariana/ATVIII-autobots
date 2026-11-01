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

import com.autobots.automanager.dto.request.ServicoRequestDTO;
import com.autobots.automanager.dto.response.ServicoResponseDTO;
import com.autobots.automanager.hateoas.AdicionadorLinkServico;
import com.autobots.automanager.service.ServicoService;

@RestController
@RequestMapping("/servicos")
public class ServicoControle {

    @Autowired
    private ServicoService service;

    @Autowired
    private AdicionadorLinkServico adicionadorLink;

    @GetMapping
    public ResponseEntity<CollectionModel<ServicoResponseDTO>> obterServicos() {
        List<ServicoResponseDTO> lista = service.obterServicos();
        adicionadorLink.adicionarLink(lista);

        CollectionModel<ServicoResponseDTO> colecao = CollectionModel.of(lista);
        colecao.add(linkTo(methodOn(ServicoControle.class).obterServicos()).withSelfRel());
        colecao.add(linkTo(methodOn(ServicoControle.class).cadastrarServico(null)).withRel("cadastrar"));
        return ResponseEntity.ok(colecao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> obterServico(@PathVariable Long id) {
        ServicoResponseDTO resposta = service.obterServico(id);
        adicionadorLink.adicionarLink(resposta);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> cadastrarServico(
            @Valid @RequestBody ServicoRequestDTO dto) {
        ServicoResponseDTO resposta = service.cadastrarServico(dto);
        adicionadorLink.adicionarLink(resposta);
        return ResponseEntity
                .created(linkTo(methodOn(ServicoControle.class).obterServico(resposta.getId())).toUri())
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> atualizarServico(
            @PathVariable Long id,
            @Valid @RequestBody ServicoRequestDTO dto) {
        ServicoResponseDTO resposta = service.atualizarServico(id, dto);
        adicionadorLink.adicionarLink(resposta);
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirServico(@PathVariable Long id) {
        service.excluirServico(id);
        return ResponseEntity.noContent().build();
    }
}