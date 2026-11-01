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

import com.autobots.automanager.dto.request.EmpresaRequestDTO;
import com.autobots.automanager.dto.response.EmpresaResponseDTO;
import com.autobots.automanager.hateoas.AdicionadorLinkEmpresa;
import com.autobots.automanager.service.EmpresaService;

@RestController
@RequestMapping("/empresas")
public class EmpresaControle {

    @Autowired
    private EmpresaService service;

    @Autowired
    private AdicionadorLinkEmpresa adicionadorLink;

    @GetMapping
    public ResponseEntity<CollectionModel<EmpresaResponseDTO>> obterEmpresas() {
        List<EmpresaResponseDTO> lista = service.obterEmpresas();
        adicionadorLink.adicionarLink(lista);

        CollectionModel<EmpresaResponseDTO> colecao = CollectionModel.of(lista);
        colecao.add(linkTo(methodOn(EmpresaControle.class).obterEmpresas()).withSelfRel());
        colecao.add(linkTo(methodOn(EmpresaControle.class).cadastrarEmpresa(null)).withRel("cadastrar"));

        return ResponseEntity.ok(colecao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> obterEmpresa(@PathVariable Long id) {
        EmpresaResponseDTO resposta = service.obterEmpresa(id);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> cadastrarEmpresa(
            @Valid @RequestBody EmpresaRequestDTO dto) {

        EmpresaResponseDTO resposta = service.cadastrarEmpresa(dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity
                .created(linkTo(methodOn(EmpresaControle.class).obterEmpresa(resposta.getId())).toUri())
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizarEmpresa(
            @PathVariable Long id,
            @Valid @RequestBody EmpresaRequestDTO dto) {

        EmpresaResponseDTO resposta = service.atualizarEmpresa(id, dto);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEmpresa(@PathVariable Long id) {
        service.excluirEmpresa(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/usuarios/{usuarioId}")
    public ResponseEntity<EmpresaResponseDTO> associarUsuario(
            @PathVariable Long id,
            @PathVariable Long usuarioId) {

        EmpresaResponseDTO resposta = service.associarUsuario(id, usuarioId);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}/usuarios/{usuarioId}")
    public ResponseEntity<Void> desassociarUsuario(
            @PathVariable Long id,
            @PathVariable Long usuarioId) {

        service.desassociarUsuario(id, usuarioId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/mercadorias/{mercadoriaId}")
    public ResponseEntity<EmpresaResponseDTO> associarMercadoria(
            @PathVariable Long id,
            @PathVariable Long mercadoriaId) {

        EmpresaResponseDTO resposta = service.associarMercadoria(id, mercadoriaId);
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

    @PostMapping("/{id}/servicos/{servicoId}")
    public ResponseEntity<EmpresaResponseDTO> associarServico(
            @PathVariable Long id,
            @PathVariable Long servicoId) {

        EmpresaResponseDTO resposta = service.associarServico(id, servicoId);
        adicionadorLink.adicionarLink(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}/servicos/{servicoId}")
    public ResponseEntity<Void> desassociarServico(
            @PathVariable Long id,
            @PathVariable Long servicoId) {

        service.desassociarServico(id, servicoId);

        return ResponseEntity.noContent().build();
    }
}