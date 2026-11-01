package com.autobots.automanager.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.request.DocumentoRequestDTO;
import com.autobots.automanager.dto.request.EmailRequestDTO;
import com.autobots.automanager.dto.request.TelefoneRequestDTO;
import com.autobots.automanager.dto.request.UsuarioRequestDTO;
import com.autobots.automanager.dto.response.CredencialResumoDTO;
import com.autobots.automanager.dto.response.DocumentoResponseDTO;
import com.autobots.automanager.dto.response.EmailResponseDTO;
import com.autobots.automanager.dto.response.MercadoriaResumoDTO;
import com.autobots.automanager.dto.response.TelefoneResponseDTO;
import com.autobots.automanager.dto.response.UsuarioResponseDTO;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;

@Component
public class UsuarioMapper {

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private DocumentoMapper documentoMapper;

    @Autowired
    private EmailMapper emailMapper;

    public Usuario paraEntidade(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setNomeSocial(dto.getNomeSocial());
        usuario.getPerfis().addAll(dto.getPerfis());

        if (dto.getTelefones() != null) {
            for (TelefoneRequestDTO item : dto.getTelefones()) {
                usuario.getTelefones().add(telefoneMapper.paraEntidade(item));
            }
        }

        if (dto.getEndereco() != null) {
            usuario.setEndereco(enderecoMapper.paraEntidade(dto.getEndereco()));
        }

        if (dto.getDocumentos() != null) {
            for (DocumentoRequestDTO item : dto.getDocumentos()) {
                usuario.getDocumentos().add(documentoMapper.paraEntidade(item));
            }
        }

        if (dto.getEmails() != null) {
            for (EmailRequestDTO item : dto.getEmails()) {
                usuario.getEmails().add(emailMapper.paraEntidade(item));
            }
        }

        return usuario;
    }

    public UsuarioResponseDTO paraResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();

        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setNomeSocial(usuario.getNomeSocial());
        dto.setPerfis(new HashSet<>(usuario.getPerfis()));

        List<TelefoneResponseDTO> telefones = new ArrayList<>();
        for (Telefone telefone : usuario.getTelefones()) {
            telefones.add(telefoneMapper.paraResponseDTO(telefone));
        }
        dto.setTelefones(telefones);

        if (usuario.getEndereco() != null) {
            dto.setEndereco(enderecoMapper.paraResponseDTO(usuario.getEndereco()));
        }

        List<DocumentoResponseDTO> documentos = new ArrayList<>();
        for (Documento documento : usuario.getDocumentos()) {
            documentos.add(documentoMapper.paraResponseDTO(documento));
        }
        dto.setDocumentos(documentos);

        List<EmailResponseDTO> emails = new ArrayList<>();
        for (Email email : usuario.getEmails()) {
            emails.add(emailMapper.paraResponseDTO(email));
        }
        dto.setEmails(emails);

        List<CredencialResumoDTO> credenciais = new ArrayList<>();
        for (Credencial credencial : usuario.getCredenciais()) {
            CredencialResumoDTO resumo = new CredencialResumoDTO();

            resumo.setId(credencial.getId());

            if (credencial instanceof CredencialUsuarioSenha) {
                resumo.setTipo("USUARIO_SENHA");
            } else if (credencial instanceof CredencialCodigoBarra) {
                resumo.setTipo("CODIGO_BARRA");
            }

            credenciais.add(resumo);
        }
        dto.setCredenciais(credenciais);

        List<MercadoriaResumoDTO> mercadorias = new ArrayList<>();
        for (Mercadoria mercadoria : usuario.getMercadorias()) {
            MercadoriaResumoDTO resumo = new MercadoriaResumoDTO();

            resumo.setId(mercadoria.getId());
            resumo.setNome(mercadoria.getNome());

            mercadorias.add(resumo);
        }
        dto.setMercadorias(mercadorias);

        return dto;
    }

    public void atualizarEntidade(Usuario usuario, UsuarioRequestDTO dto) {
        usuario.setNome(dto.getNome());
        usuario.setNomeSocial(dto.getNomeSocial());

        usuario.getPerfis().clear();
        usuario.getPerfis().addAll(dto.getPerfis());

        if (dto.getTelefones() != null) {
            usuario.getTelefones().clear();

            for (TelefoneRequestDTO item : dto.getTelefones()) {
                usuario.getTelefones().add(telefoneMapper.paraEntidade(item));
            }
        }

        if (dto.getEmails() != null) {
            usuario.getEmails().clear();

            for (EmailRequestDTO item : dto.getEmails()) {
                usuario.getEmails().add(emailMapper.paraEntidade(item));
            }
        }

        if (dto.getEndereco() != null) {
            if (usuario.getEndereco() == null) {
                usuario.setEndereco(enderecoMapper.paraEntidade(dto.getEndereco()));
            } else {
                enderecoMapper.atualizarEntidade(usuario.getEndereco(), dto.getEndereco());
            }
        }

        if (dto.getDocumentos() != null) {
            List<String> numerosRecebidos = new ArrayList<>();

            for (DocumentoRequestDTO item : dto.getDocumentos()) {
                numerosRecebidos.add(item.getNumero());

                Documento existente = null;
                for (Documento atual : usuario.getDocumentos()) {
                    if (atual.getNumero().equals(item.getNumero())) {
                        existente = atual;
                        break;
                    }
                }

                if (existente != null) {
                    documentoMapper.atualizarEntidade(existente, item);
                } else {
                    usuario.getDocumentos().add(documentoMapper.paraEntidade(item));
                }
            }

            usuario.getDocumentos().removeIf(
                    documento -> !numerosRecebidos.contains(documento.getNumero()));
        }
    }
}