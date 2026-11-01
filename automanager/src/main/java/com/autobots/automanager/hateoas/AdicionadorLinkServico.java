package com.autobots.automanager.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.dto.response.ServicoResponseDTO;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<ServicoResponseDTO> {

    @Override
    public void adicionarLink(List<ServicoResponseDTO> lista) {
        for (ServicoResponseDTO dto : lista) {
            adicionarLinkProprio(dto);
        }
    }

    @Override
    public void adicionarLink(ServicoResponseDTO objeto) {
        adicionarLinkProprio(objeto);

        objeto.add(linkTo(methodOn(ServicoControle.class).obterServicos())
                .withRel("servicos"));

        objeto.add(linkTo(methodOn(ServicoControle.class).atualizarServico(objeto.getId(), null))
                .withRel("atualizar"));

        objeto.add(linkTo(methodOn(ServicoControle.class).excluirServico(objeto.getId()))
                .withRel("excluir"));
    }

    private void adicionarLinkProprio(ServicoResponseDTO dto) {
        dto.add(linkTo(methodOn(ServicoControle.class).obterServico(dto.getId()))
                .withSelfRel());
    }
}