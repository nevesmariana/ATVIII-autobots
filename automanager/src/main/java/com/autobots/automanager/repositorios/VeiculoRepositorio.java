package com.autobots.automanager.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Veiculo;

public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {

    List<Veiculo> findByProprietario_Id(Long proprietarioId);

    boolean existsByProprietario_Id(Long proprietarioId);

    boolean existsByPlaca(String placa);

    boolean existsByPlacaAndIdNot(String placa, Long id);
}