package com.autobots.automanager.controles;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaizControle {

    public static class Raiz extends RepresentationModel<Raiz> {
    }

    @GetMapping("/")
    public ResponseEntity<Raiz> raiz() {
        Raiz raiz = new Raiz();

        raiz.add(linkTo(methodOn(RaizControle.class).raiz()).withSelfRel());
        raiz.add(linkTo(methodOn(DocumentoControle.class).obterDocumentos()).withRel("documentos"));
        raiz.add(linkTo(methodOn(EnderecoControle.class).obterEnderecos()).withRel("enderecos"));
        raiz.add(linkTo(methodOn(TelefoneControle.class).obterTelefones()).withRel("telefones"));
        raiz.add(linkTo(methodOn(ServicoControle.class).obterServicos()).withRel("servicos"));
        raiz.add(linkTo(methodOn(MercadoriaControle.class).obterMercadorias()).withRel("mercadorias"));
        raiz.add(linkTo(methodOn(EmailControle.class).obterEmails()).withRel("emails"));
        raiz.add(linkTo(methodOn(CredencialUsuarioSenhaControle.class).obterCredenciais()).withRel("credenciais-usuario-senha"));
        raiz.add(linkTo(methodOn(CredencialCodigoBarraControle.class).obterCredenciais()).withRel("credenciais-codigo-barra"));
        raiz.add(linkTo(methodOn(UsuarioControle.class).obterUsuarios(null)).withRel("usuarios"));
        raiz.add(linkTo(methodOn(VeiculoControle.class).obterVeiculos(null)).withRel("veiculos"));
        raiz.add(linkTo(methodOn(EmpresaControle.class).obterEmpresas()).withRel("empresas"));
        raiz.add(linkTo(methodOn(VendaControle.class).obterVendas(null, null)).withRel("vendas"));

        return ResponseEntity.ok(raiz);
    }
}