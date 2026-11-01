package com.autobots.automanager.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.CredencialCodigoBarraControle;
import com.autobots.automanager.controles.CredencialUsuarioSenhaControle;
import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.dto.response.CredencialResumoDTO;
import com.autobots.automanager.dto.response.MercadoriaResumoDTO;
import com.autobots.automanager.dto.response.UsuarioResponseDTO;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<UsuarioResponseDTO> {

    @Autowired
    private AdicionadorLinkDocumento adicionadorLinkDocumento;

    @Autowired
    private AdicionadorLinkEndereco adicionadorLinkEndereco;

    @Autowired
    private AdicionadorLinkTelefone adicionadorLinkTelefone;

    @Autowired
    private AdicionadorLinkEmail adicionadorLinkEmail;

    @Override
    public void adicionarLink(List<UsuarioResponseDTO> lista) {
        for (UsuarioResponseDTO usuario : lista) {
            adicionarLinkProprio(usuario);
            adicionarLinksRelacionados(usuario);
        }
    }

    @Override
    public void adicionarLink(UsuarioResponseDTO objeto) {
        adicionarLinkProprio(objeto);
        adicionarLinksRelacionados(objeto);

        objeto.add(linkTo(methodOn(UsuarioControle.class).obterUsuarios(null))
                .withRel("usuarios"));

        objeto.add(linkTo(methodOn(UsuarioControle.class).atualizarUsuario(objeto.getId(), null))
                .withRel("atualizar"));

        objeto.add(linkTo(methodOn(UsuarioControle.class).excluirUsuario(objeto.getId()))
                .withRel("excluir"));

        objeto.add(linkTo(methodOn(VeiculoControle.class).obterVeiculos(objeto.getId()))
                .withRel("veiculos"));

        objeto.add(linkTo(methodOn(VendaControle.class).obterVendas(null, objeto.getId()))
                .withRel("compras"));
    }

    private void adicionarLinkProprio(UsuarioResponseDTO usuario) {
        usuario.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(usuario.getId()))
                .withSelfRel());
    }

    private void adicionarLinksRelacionados(UsuarioResponseDTO usuario) {
        if (usuario.getTelefones() != null) {
            adicionadorLinkTelefone.adicionarLink(usuario.getTelefones());
        }

        if (usuario.getEndereco() != null) {
            adicionadorLinkEndereco.adicionarLink(List.of(usuario.getEndereco()));
        }

        if (usuario.getDocumentos() != null) {
            adicionadorLinkDocumento.adicionarLink(usuario.getDocumentos());
        }

        if (usuario.getEmails() != null) {
            adicionadorLinkEmail.adicionarLink(usuario.getEmails());
        }

        if (usuario.getCredenciais() != null) {
            for (CredencialResumoDTO credencial : usuario.getCredenciais()) {
                if ("USUARIO_SENHA".equals(credencial.getTipo())) {
                    credencial.add(linkTo(methodOn(CredencialUsuarioSenhaControle.class)
                            .obterCredencial(credencial.getId())).withSelfRel());
                } else if ("CODIGO_BARRA".equals(credencial.getTipo())) {
                    credencial.add(linkTo(methodOn(CredencialCodigoBarraControle.class)
                            .obterCredencial(credencial.getId())).withSelfRel());
                }

                credencial.add(linkTo(methodOn(UsuarioControle.class)
                        .desassociarCredencial(usuario.getId(), credencial.getId()))
                        .withRel("desassociar"));
            }
        }

        if (usuario.getMercadorias() != null) {
            for (MercadoriaResumoDTO mercadoria : usuario.getMercadorias()) {
                mercadoria.add(linkTo(methodOn(MercadoriaControle.class)
                        .obterMercadoria(mercadoria.getId())).withSelfRel());

                mercadoria.add(linkTo(methodOn(UsuarioControle.class)
                        .desassociarMercadoria(usuario.getId(), mercadoria.getId()))
                        .withRel("desassociar"));
            }
        }
    }
}