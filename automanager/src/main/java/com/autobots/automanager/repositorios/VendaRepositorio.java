package com.autobots.automanager.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {

    List<Venda> findByEmpresa_Id(Long empresaId);

    boolean existsByIdentificacao(String identificacao);

    boolean existsByIdentificacaoAndIdNot(String identificacao, Long id);

    boolean existsByCliente_Id(Long usuarioId);

    boolean existsByFuncionario_Id(Long usuarioId);

    boolean existsByVeiculo_Id(Long veiculoId);

    boolean existsByMercadorias_Id(Long mercadoriaId);

    boolean existsByServicos_Id(Long servicoId);

    boolean existsByCliente_IdAndEmpresa_IdNot(Long clienteId, Long empresaId);
}