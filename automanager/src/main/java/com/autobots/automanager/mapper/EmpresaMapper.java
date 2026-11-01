package com.autobots.automanager.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.request.EmpresaRequestDTO;
import com.autobots.automanager.dto.request.TelefoneRequestDTO;
import com.autobots.automanager.dto.response.EmpresaResponseDTO;
import com.autobots.automanager.dto.response.MercadoriaResumoDTO;
import com.autobots.automanager.dto.response.ServicoResumoDTO;
import com.autobots.automanager.dto.response.TelefoneResponseDTO;
import com.autobots.automanager.dto.response.UsuarioResumoDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;

@Component
public class EmpresaMapper {

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Autowired
    private EnderecoMapper enderecoMapper;

    public Empresa paraEntidade(EmpresaRequestDTO dto) {
        Empresa empresa = new Empresa();

        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setCadastro(new Date());

        if (dto.getTelefones() != null) {
            for (TelefoneRequestDTO item : dto.getTelefones()) {
                empresa.getTelefones().add(telefoneMapper.paraEntidade(item));
            }
        }

        if (dto.getEndereco() != null) {
            empresa.setEndereco(enderecoMapper.paraEntidade(dto.getEndereco()));
        }

        return empresa;
    }

    public EmpresaResponseDTO paraResponseDTO(Empresa empresa) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();

        dto.setId(empresa.getId());
        dto.setRazaoSocial(empresa.getRazaoSocial());
        dto.setNomeFantasia(empresa.getNomeFantasia());
        dto.setCadastro(empresa.getCadastro());

        List<TelefoneResponseDTO> telefones = new ArrayList<>();
        for (Telefone telefone : empresa.getTelefones()) {
            telefones.add(telefoneMapper.paraResponseDTO(telefone));
        }
        dto.setTelefones(telefones);

        if (empresa.getEndereco() != null) {
            dto.setEndereco(enderecoMapper.paraResponseDTO(empresa.getEndereco()));
        }

        List<UsuarioResumoDTO> usuarios = new ArrayList<>();
        for (Usuario usuario : empresa.getUsuarios()) {
            UsuarioResumoDTO resumo = new UsuarioResumoDTO();
            resumo.setId(usuario.getId());
            resumo.setNome(usuario.getNome());
            usuarios.add(resumo);
        }
        dto.setUsuarios(usuarios);

        List<MercadoriaResumoDTO> mercadorias = new ArrayList<>();
        for (Mercadoria mercadoria : empresa.getMercadorias()) {
            MercadoriaResumoDTO resumo = new MercadoriaResumoDTO();
            resumo.setId(mercadoria.getId());
            resumo.setNome(mercadoria.getNome());
            mercadorias.add(resumo);
        }
        dto.setMercadorias(mercadorias);

        List<ServicoResumoDTO> servicos = new ArrayList<>();
        for (Servico servico : empresa.getServicos()) {
            ServicoResumoDTO resumo = new ServicoResumoDTO();
            resumo.setId(servico.getId());
            resumo.setNome(servico.getNome());
            servicos.add(resumo);
        }
        dto.setServicos(servicos);

        return dto;
    }

    public void atualizarEntidade(Empresa empresa, EmpresaRequestDTO dto) {
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setNomeFantasia(dto.getNomeFantasia());

        if (dto.getTelefones() != null) {
            empresa.getTelefones().clear();

            for (TelefoneRequestDTO item : dto.getTelefones()) {
                empresa.getTelefones().add(telefoneMapper.paraEntidade(item));
            }
        }

        if (dto.getEndereco() != null) {
            if (empresa.getEndereco() == null) {
                empresa.setEndereco(enderecoMapper.paraEntidade(dto.getEndereco()));
            } else {
                enderecoMapper.atualizarEntidade(empresa.getEndereco(), dto.getEndereco());
            }
        }
    }
}