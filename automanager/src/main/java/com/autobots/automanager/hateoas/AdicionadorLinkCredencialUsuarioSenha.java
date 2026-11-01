package com.autobots.automanager.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.CredencialUsuarioSenhaControle;
import com.autobots.automanager.dto.response.CredencialUsuarioSenhaResponseDTO;

@Component
public class AdicionadorLinkCredencialUsuarioSenha implements AdicionadorLink<CredencialUsuarioSenhaResponseDTO> {

    @Override
    public void adicionarLink(List<CredencialUsuarioSenhaResponseDTO> lista) {
        for (CredencialUsuarioSenhaResponseDTO dto : lista) {
            adicionarLinkProprio(dto);
        }
    }

    @Override
    public void adicionarLink(CredencialUsuarioSenhaResponseDTO objeto) {
        adicionarLinkProprio(objeto);

        objeto.add(linkTo(methodOn(CredencialUsuarioSenhaControle.class).obterCredenciais())
                .withRel("credenciais"));

        objeto.add(linkTo(methodOn(CredencialUsuarioSenhaControle.class).atualizarCredencial(objeto.getId(), null))
                .withRel("atualizar"));

        objeto.add(linkTo(methodOn(CredencialUsuarioSenhaControle.class).excluirCredencial(objeto.getId()))
                .withRel("excluir"));
    }

    private void adicionarLinkProprio(CredencialUsuarioSenhaResponseDTO dto) {
        dto.add(linkTo(methodOn(CredencialUsuarioSenhaControle.class).obterCredencial(dto.getId()))
                .withSelfRel());
    }
}