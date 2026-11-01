package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.ServicoRequestDTO;
import com.autobots.automanager.dto.response.ServicoResponseDTO;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.excecoes.ServicoNaoEncontradoException;
import com.autobots.automanager.mapper.ServicoMapper;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.excecoes.ExclusaoNaoPermitidaException;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepositorio repositorio;

    @Autowired
    private ServicoMapper mapper;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    public List<ServicoResponseDTO> obterServicos() {
        List<Servico> servicos = repositorio.findAll();

        List<ServicoResponseDTO> dtos = new ArrayList<>();

        for (Servico servico : servicos) {
            dtos.add(mapper.paraResponseDTO(servico));
        }
        return dtos;
    }

    public ServicoResponseDTO obterServico(Long id) {
        Servico servico = repositorio.findById(id)
                .orElseThrow(() -> new ServicoNaoEncontradoException(id));
        return mapper.paraResponseDTO(servico);
    }

    public ServicoResponseDTO cadastrarServico(ServicoRequestDTO dto) {
        Servico servico = mapper.paraEntidade(dto);
        servico = repositorio.save(servico);
        return mapper.paraResponseDTO(servico);
    }

    public ServicoResponseDTO atualizarServico(Long id, ServicoRequestDTO dto) {
        Servico servico = repositorio.findById(id)
                .orElseThrow(() -> new ServicoNaoEncontradoException(id));
        mapper.atualizarEntidade(servico, dto);
        servico = repositorio.save(servico);
        return mapper.paraResponseDTO(servico);
    }

    public void excluirServico(Long id) {
        Servico servico = repositorio.findById(id)
                .orElseThrow(() -> new ServicoNaoEncontradoException(id));

        if (vendaRepositorio.existsByServicos_Id(id)) {
            throw new ExclusaoNaoPermitidaException(
                    "O serviço " + id + " está em vendas e não pode ser excluído");
        }

        repositorio.delete(servico);
    }
}