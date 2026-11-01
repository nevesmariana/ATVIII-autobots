package com.autobots.automanager.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.request.VendaRequestDTO;
import com.autobots.automanager.dto.response.EmpresaResumoDTO;
import com.autobots.automanager.dto.response.MercadoriaResumoDTO;
import com.autobots.automanager.dto.response.ServicoResumoDTO;
import com.autobots.automanager.dto.response.UsuarioResumoDTO;
import com.autobots.automanager.dto.response.VeiculoResumoDTO;
import com.autobots.automanager.dto.response.VendaResponseDTO;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;

@Component
public class VendaMapper {

    public Venda paraEntidade(VendaRequestDTO dto) {
        Venda venda = new Venda();

        venda.setIdentificacao(dto.getIdentificacao());
        venda.setCadastro(new Date());

        return venda;
    }

    public void atualizarEntidade(Venda venda, VendaRequestDTO dto) {
        venda.setIdentificacao(dto.getIdentificacao());
    }

    public VendaResponseDTO paraResponseDTO(Venda venda) {
        VendaResponseDTO dto = new VendaResponseDTO();

        dto.setId(venda.getId());
        dto.setIdentificacao(venda.getIdentificacao());
        dto.setCadastro(venda.getCadastro());

        EmpresaResumoDTO empresa = new EmpresaResumoDTO();
        empresa.setId(venda.getEmpresa().getId());
        empresa.setRazaoSocial(venda.getEmpresa().getRazaoSocial());
        dto.setEmpresa(empresa);

        dto.setCliente(resumirUsuario(venda.getCliente()));
        dto.setFuncionario(resumirUsuario(venda.getFuncionario()));

        VeiculoResumoDTO veiculo = new VeiculoResumoDTO();
        veiculo.setId(venda.getVeiculo().getId());
        veiculo.setModelo(venda.getVeiculo().getModelo());
        veiculo.setPlaca(venda.getVeiculo().getPlaca());
        dto.setVeiculo(veiculo);

        BigDecimal total = BigDecimal.ZERO;

        List<MercadoriaResumoDTO> mercadorias = new ArrayList<>();
        for (Mercadoria mercadoria : venda.getMercadorias()) {
            MercadoriaResumoDTO resumo = new MercadoriaResumoDTO();
            resumo.setId(mercadoria.getId());
            resumo.setNome(mercadoria.getNome());
            mercadorias.add(resumo);

            total = total.add(BigDecimal.valueOf(mercadoria.getValor()));
        }
        dto.setMercadorias(mercadorias);

        List<ServicoResumoDTO> servicos = new ArrayList<>();
        for (Servico servico : venda.getServicos()) {
            ServicoResumoDTO resumo = new ServicoResumoDTO();
            resumo.setId(servico.getId());
            resumo.setNome(servico.getNome());
            servicos.add(resumo);

            total = total.add(BigDecimal.valueOf(servico.getValor()));
        }
        dto.setServicos(servicos);

        dto.setValorTotal(total.setScale(2, RoundingMode.HALF_UP).doubleValue());

        return dto;
    }

    private UsuarioResumoDTO resumirUsuario(Usuario usuario) {
        UsuarioResumoDTO resumo = new UsuarioResumoDTO();

        resumo.setId(usuario.getId());
        resumo.setNome(usuario.getNome());

        return resumo;
    }
}