package com.autobots.automanager.dto.response;

import java.util.List;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioResponseDTO extends RepresentationModel<UsuarioResponseDTO> {

    private Long id;
    private String nome;
    private String nomeSocial;
    private Set<PerfilUsuario> perfis;
    private List<TelefoneResponseDTO> telefones;
    private EnderecoResponseDTO endereco;
    private List<DocumentoResponseDTO> documentos;
    private List<EmailResponseDTO> emails;
    private List<CredencialResumoDTO> credenciais;
    private List<MercadoriaResumoDTO> mercadorias;
}