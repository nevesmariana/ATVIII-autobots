package com.autobots.automanager.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmailControle;
import com.autobots.automanager.dto.response.EmailResponseDTO;

@Component
public class AdicionadorLinkEmail implements AdicionadorLink<EmailResponseDTO> {

    @Override
    public void adicionarLink(List<EmailResponseDTO> lista) {
        for (EmailResponseDTO dto : lista) {
            adicionarLinkProprio(dto);
        }
    }

    @Override
    public void adicionarLink(EmailResponseDTO objeto) {
        adicionarLinkProprio(objeto);

        objeto.add(linkTo(methodOn(EmailControle.class).obterEmails())
                .withRel("emails"));

        objeto.add(linkTo(methodOn(EmailControle.class).atualizarEmail(objeto.getId(), null))
                .withRel("atualizar"));

        objeto.add(linkTo(methodOn(EmailControle.class).excluirEmail(objeto.getId()))
                .withRel("excluir"));
    }

    private void adicionarLinkProprio(EmailResponseDTO dto) {
        dto.add(linkTo(methodOn(EmailControle.class).obterEmail(dto.getId()))
                .withSelfRel());
    }
}