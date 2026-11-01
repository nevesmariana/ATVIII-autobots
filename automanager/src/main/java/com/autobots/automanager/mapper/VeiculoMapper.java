package com.autobots.automanager.mapper;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.request.VeiculoRequestDTO;
import com.autobots.automanager.dto.response.UsuarioResumoDTO;
import com.autobots.automanager.dto.response.VeiculoResponseDTO;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;

@Component
public class VeiculoMapper {

    public String normalizarPlaca(String placa) {
        return placa.replace("-", "").toUpperCase();
    }

    public Veiculo paraEntidade(VeiculoRequestDTO dto, Usuario proprietario) {
        Veiculo veiculo = new Veiculo();

        veiculo.setTipo(dto.getTipo());
        veiculo.setModelo(dto.getModelo());
        veiculo.setPlaca(normalizarPlaca(dto.getPlaca()));
        veiculo.setProprietario(proprietario);

        return veiculo;
    }

    public VeiculoResponseDTO paraResponseDTO(Veiculo veiculo) {
        VeiculoResponseDTO dto = new VeiculoResponseDTO();

        dto.setId(veiculo.getId());
        dto.setTipo(veiculo.getTipo());
        dto.setModelo(veiculo.getModelo());
        dto.setPlaca(veiculo.getPlaca());

        UsuarioResumoDTO proprietario = new UsuarioResumoDTO();
        proprietario.setId(veiculo.getProprietario().getId());
        proprietario.setNome(veiculo.getProprietario().getNome());
        dto.setProprietario(proprietario);

        return dto;
    }

    public void atualizarEntidade(Veiculo veiculo, VeiculoRequestDTO dto, Usuario proprietario) {
        veiculo.setTipo(dto.getTipo());
        veiculo.setModelo(dto.getModelo());
        veiculo.setPlaca(normalizarPlaca(dto.getPlaca()));
        veiculo.setProprietario(proprietario);
    }
}