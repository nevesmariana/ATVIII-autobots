package com.autobots.automanager.mapper;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.request.DocumentoRequestDTO;
import com.autobots.automanager.dto.response.DocumentoResponseDTO;
import com.autobots.automanager.entidades.Documento;

@Component
public class DocumentoMapper {

    public Documento paraEntidade(DocumentoRequestDTO dto) {Documento documento = new Documento();
        documento.setTipo(dto.getTipo());
        documento.setNumero(dto.getNumero());
        documento.setDataEmissao(dto.getDataEmissao());

        return documento;
    }

    public DocumentoResponseDTO paraResponseDTO(Documento documento) { DocumentoResponseDTO dto = new DocumentoResponseDTO();
        dto.setId(documento.getId());
        dto.setTipo(documento.getTipo());
        dto.setDataEmissao(documento.getDataEmissao());
        dto.setNumero(documento.getNumero());

        return dto;
    }

    public void atualizarEntidade( Documento documento,DocumentoRequestDTO dto) {
        documento.setTipo(dto.getTipo());
        documento.setDataEmissao(dto.getDataEmissao());
        documento.setNumero(dto.getNumero());
    }
}