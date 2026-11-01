package com.autobots.automanager.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.dto.response.MercadoriaResponseDTO;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<MercadoriaResponseDTO> {

    @Override
    public void adicionarLink(List<MercadoriaResponseDTO> lista) {
        for (MercadoriaResponseDTO dto : lista) {
            adicionarLinkProprio(dto);
        }
    }

    @Override
    public void adicionarLink(MercadoriaResponseDTO objeto) {
        adicionarLinkProprio(objeto);

        objeto.add(linkTo(methodOn(MercadoriaControle.class).obterMercadorias())
                .withRel("mercadorias"));

        objeto.add(linkTo(methodOn(MercadoriaControle.class).atualizarMercadoria(objeto.getId(), null))
                .withRel("atualizar"));

        objeto.add(linkTo(methodOn(MercadoriaControle.class).excluirMercadoria(objeto.getId()))
                .withRel("excluir"));
    }

    private void adicionarLinkProprio(MercadoriaResponseDTO dto) {
        dto.add(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(dto.getId()))
                .withSelfRel());
    }
}