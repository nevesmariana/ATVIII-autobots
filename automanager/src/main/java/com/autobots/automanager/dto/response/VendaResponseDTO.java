package com.autobots.automanager.dto.response;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class VendaResponseDTO extends RepresentationModel<VendaResponseDTO> {

    private Long id;
    private String identificacao;
    private Date cadastro;
    private EmpresaResumoDTO empresa;
    private UsuarioResumoDTO cliente;
    private UsuarioResumoDTO funcionario;
    private VeiculoResumoDTO veiculo;
    private List<MercadoriaResumoDTO> mercadorias;
    private List<ServicoResumoDTO> servicos;
    private double valorTotal;
}