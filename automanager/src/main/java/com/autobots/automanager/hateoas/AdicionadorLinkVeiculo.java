package com.autobots.automanager.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.dto.response.VeiculoResponseDTO;

@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink<VeiculoResponseDTO> {

    @Override
    public void adicionarLink(List<VeiculoResponseDTO> lista) {
        for (VeiculoResponseDTO dto : lista) {
            adicionarLinkProprio(dto);
            adicionarLinkProprietario(dto);
        }
    }

    @Override
    public void adicionarLink(VeiculoResponseDTO objeto) {
        adicionarLinkProprio(objeto);
        adicionarLinkProprietario(objeto);

        objeto.add(linkTo(methodOn(VeiculoControle.class).obterVeiculos(null))
                .withRel("veiculos"));

        objeto.add(linkTo(methodOn(VeiculoControle.class).atualizarVeiculo(objeto.getId(), null))
                .withRel("atualizar"));

        objeto.add(linkTo(methodOn(VeiculoControle.class).excluirVeiculo(objeto.getId()))
                .withRel("excluir"));
    }

    private void adicionarLinkProprio(VeiculoResponseDTO dto) {
        dto.add(linkTo(methodOn(VeiculoControle.class).obterVeiculo(dto.getId()))
                .withSelfRel());
    }

    private void adicionarLinkProprietario(VeiculoResponseDTO dto) {
        if (dto.getProprietario() != null) {
            dto.getProprietario().add(linkTo(methodOn(UsuarioControle.class)
                    .obterUsuario(dto.getProprietario().getId()))
                    .withSelfRel());
        }
    }
}