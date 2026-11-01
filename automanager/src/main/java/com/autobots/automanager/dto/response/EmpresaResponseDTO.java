package com.autobots.automanager.dto.response;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmpresaResponseDTO extends RepresentationModel<EmpresaResponseDTO> {

    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private Date cadastro;
    private List<TelefoneResponseDTO> telefones;
    private EnderecoResponseDTO endereco;
    private List<UsuarioResumoDTO> usuarios;
    private List<MercadoriaResumoDTO> mercadorias;
    private List<ServicoResumoDTO> servicos;
}