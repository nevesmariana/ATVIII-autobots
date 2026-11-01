package com.autobots.automanager.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.dto.response.EmpresaResponseDTO;
import com.autobots.automanager.dto.response.MercadoriaResumoDTO;
import com.autobots.automanager.dto.response.ServicoResumoDTO;
import com.autobots.automanager.dto.response.UsuarioResumoDTO;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<EmpresaResponseDTO> {

    @Autowired
    private AdicionadorLinkTelefone adicionadorLinkTelefone;

    @Autowired
    private AdicionadorLinkEndereco adicionadorLinkEndereco;

    @Override
    public void adicionarLink(List<EmpresaResponseDTO> lista) {
        for (EmpresaResponseDTO empresa : lista) {
            adicionarLinkProprio(empresa);
            adicionarLinksRelacionados(empresa);
        }
    }

    @Override
    public void adicionarLink(EmpresaResponseDTO objeto) {
        adicionarLinkProprio(objeto);
        adicionarLinksRelacionados(objeto);

        objeto.add(linkTo(methodOn(EmpresaControle.class).obterEmpresas())
                .withRel("empresas"));

        objeto.add(linkTo(methodOn(EmpresaControle.class).atualizarEmpresa(objeto.getId(), null))
                .withRel("atualizar"));

        objeto.add(linkTo(methodOn(EmpresaControle.class).excluirEmpresa(objeto.getId()))
                .withRel("excluir"));

        objeto.add(linkTo(methodOn(VendaControle.class).obterVendas(objeto.getId(), null))
                .withRel("vendas"));
    }

    private void adicionarLinkProprio(EmpresaResponseDTO empresa) {
        empresa.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(empresa.getId()))
                .withSelfRel());
    }

    private void adicionarLinksRelacionados(EmpresaResponseDTO empresa) {
        if (empresa.getTelefones() != null) {
            adicionadorLinkTelefone.adicionarLink(empresa.getTelefones());
        }

        if (empresa.getEndereco() != null) {
            adicionadorLinkEndereco.adicionarLink(List.of(empresa.getEndereco()));
        }

        if (empresa.getUsuarios() != null) {
            for (UsuarioResumoDTO usuario : empresa.getUsuarios()) {
                usuario.add(linkTo(methodOn(UsuarioControle.class)
                        .obterUsuario(usuario.getId())).withSelfRel());

                usuario.add(linkTo(methodOn(EmpresaControle.class)
                        .desassociarUsuario(empresa.getId(), usuario.getId()))
                        .withRel("desassociar"));
            }
        }

        if (empresa.getMercadorias() != null) {
            for (MercadoriaResumoDTO mercadoria : empresa.getMercadorias()) {
                mercadoria.add(linkTo(methodOn(MercadoriaControle.class)
                        .obterMercadoria(mercadoria.getId())).withSelfRel());

                mercadoria.add(linkTo(methodOn(EmpresaControle.class)
                        .desassociarMercadoria(empresa.getId(), mercadoria.getId()))
                        .withRel("desassociar"));
            }
        }

        if (empresa.getServicos() != null) {
            for (ServicoResumoDTO servico : empresa.getServicos()) {
                servico.add(linkTo(methodOn(ServicoControle.class)
                        .obterServico(servico.getId())).withSelfRel());

                servico.add(linkTo(methodOn(EmpresaControle.class)
                        .desassociarServico(empresa.getId(), servico.getId()))
                        .withRel("desassociar"));
            }
        }
    }
}