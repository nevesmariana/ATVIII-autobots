package com.autobots.automanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.request.VeiculoRequestDTO;
import com.autobots.automanager.dto.response.VeiculoResponseDTO;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.excecoes.PlacaDuplicadaException;
import com.autobots.automanager.excecoes.ProprietarioInvalidoException;
import com.autobots.automanager.excecoes.UsuarioNaoEncontradoException;
import com.autobots.automanager.excecoes.VeiculoNaoEncontradoException;
import com.autobots.automanager.mapper.VeiculoMapper;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.excecoes.ExclusaoNaoPermitidaException;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepositorio repositorio;

    @Autowired
    private VeiculoMapper mapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    public List<VeiculoResponseDTO> obterVeiculos(Long proprietarioId) {
        List<Veiculo> veiculos;

        if (proprietarioId == null) {
            veiculos = repositorio.findAll();
        } else {
            veiculos = repositorio.findByProprietario_Id(proprietarioId);
        }

        List<VeiculoResponseDTO> dtos = new ArrayList<>();

        for (Veiculo veiculo : veiculos) {
            dtos.add(mapper.paraResponseDTO(veiculo));
        }

        return dtos;
    }

    public VeiculoResponseDTO obterVeiculo(Long id) {
        Veiculo veiculo = repositorio.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));

        return mapper.paraResponseDTO(veiculo);
    }

    public VeiculoResponseDTO cadastrarVeiculo(VeiculoRequestDTO dto) {
        Usuario proprietario = buscarProprietario(dto.getProprietarioId());

        String placa = mapper.normalizarPlaca(dto.getPlaca());
        if (repositorio.existsByPlaca(placa)) {
            throw new PlacaDuplicadaException(placa);
        }

        Veiculo veiculo = mapper.paraEntidade(dto, proprietario);

        veiculo = repositorio.save(veiculo);

        return mapper.paraResponseDTO(veiculo);
    }

    public VeiculoResponseDTO atualizarVeiculo(Long id, VeiculoRequestDTO dto) {
        Veiculo veiculo = repositorio.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));

        Usuario proprietario = buscarProprietario(dto.getProprietarioId());

        String placa = mapper.normalizarPlaca(dto.getPlaca());
        if (repositorio.existsByPlacaAndIdNot(placa, id)) {
            throw new PlacaDuplicadaException(placa);
        }

        mapper.atualizarEntidade(veiculo, dto, proprietario);

        veiculo = repositorio.save(veiculo);

        return mapper.paraResponseDTO(veiculo);
    }

    public void excluirVeiculo(Long id) {
        Veiculo veiculo = repositorio.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));

        if (vendaRepositorio.existsByVeiculo_Id(id)) {
            throw new ExclusaoNaoPermitidaException(
                    "O veículo " + id + " possui vendas e não pode ser excluído");
        }

        repositorio.delete(veiculo);
    }

    private Usuario buscarProprietario(Long usuarioId) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        if (!usuario.getPerfis().contains(PerfilUsuario.CLIENTE)) {
            throw new ProprietarioInvalidoException(usuarioId);
        }

        return usuario;
    }
}