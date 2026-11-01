package com.autobots.automanager.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.dto.response.MercadoriaResumoDTO;
import com.autobots.automanager.dto.response.ServicoResumoDTO;
import com.autobots.automanager.dto.response.VendaResponseDTO;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<VendaResponseDTO> {

    @Override
    public void adicionarLink(List<VendaResponseDTO> lista) {
        for (VendaResponseDTO venda : lista) {
            adicionarLinkProprio(venda);
            adicionarLinksRelacionados(venda);
        }
    }

    @Override
    public void adicionarLink(VendaResponseDTO objeto) {
        adicionarLinkProprio(objeto);
        adicionarLinksRelacionados(objeto);

        objeto.add(linkTo(methodOn(VendaControle.class).obterVendas(null, null))
                .withRel("vendas"));

        objeto.add(linkTo(methodOn(VendaControle.class).atualizarVenda(objeto.getId(), null))
                .withRel("atualizar"));

        objeto.add(linkTo(methodOn(VendaControle.class).excluirVenda(objeto.getId()))
                .withRel("excluir"));
    }

    private void adicionarLinkProprio(VendaResponseDTO venda) {
        venda.add(linkTo(methodOn(VendaControle.class).obterVenda(venda.getId()))
                .withSelfRel());
    }

    private void adicionarLinksRelacionados(VendaResponseDTO venda) {
        venda.getEmpresa().add(linkTo(methodOn(EmpresaControle.class)
                .obterEmpresa(venda.getEmpresa().getId())).withSelfRel());

        venda.getCliente().add(linkTo(methodOn(UsuarioControle.class)
                .obterUsuario(venda.getCliente().getId())).withSelfRel());

        venda.getFuncionario().add(linkTo(methodOn(UsuarioControle.class)
                .obterUsuario(venda.getFuncionario().getId())).withSelfRel());

        venda.getVeiculo().add(linkTo(methodOn(VeiculoControle.class)
                .obterVeiculo(venda.getVeiculo().getId())).withSelfRel());

        for (MercadoriaResumoDTO mercadoria : venda.getMercadorias()) {
            mercadoria.add(linkTo(methodOn(MercadoriaControle.class)
                    .obterMercadoria(mercadoria.getId())).withSelfRel());
        }

        for (ServicoResumoDTO servico : venda.getServicos()) {
            servico.add(linkTo(methodOn(ServicoControle.class)
                    .obterServico(servico.getId())).withSelfRel());
        }
    }
}