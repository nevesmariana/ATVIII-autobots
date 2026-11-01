package com.autobots.automanager.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.CredencialCodigoBarraControle;
import com.autobots.automanager.dto.response.CredencialCodigoBarraResponseDTO;

@Component
public class AdicionadorLinkCredencialCodigoBarra implements AdicionadorLink<CredencialCodigoBarraResponseDTO> {

    @Override
    public void adicionarLink(List<CredencialCodigoBarraResponseDTO> lista) {
        for (CredencialCodigoBarraResponseDTO dto : lista) {
            adicionarLinkProprio(dto);
        }
    }

    @Override
    public void adicionarLink(CredencialCodigoBarraResponseDTO objeto) {
        adicionarLinkProprio(objeto);

        objeto.add(linkTo(methodOn(CredencialCodigoBarraControle.class).obterCredenciais())
                .withRel("credenciais"));

        objeto.add(linkTo(methodOn(CredencialCodigoBarraControle.class).atualizarCredencial(objeto.getId(), null))
                .withRel("atualizar"));

        objeto.add(linkTo(methodOn(CredencialCodigoBarraControle.class).excluirCredencial(objeto.getId()))
                .withRel("excluir"));
    }

    private void adicionarLinkProprio(CredencialCodigoBarraResponseDTO dto) {
        dto.add(linkTo(methodOn(CredencialCodigoBarraControle.class).obterCredencial(dto.getId()))
                .withSelfRel());
    }
}