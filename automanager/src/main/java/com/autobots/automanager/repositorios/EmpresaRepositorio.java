package com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Empresa;

public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {

    boolean existsByRazaoSocial(String razaoSocial);

    boolean existsByRazaoSocialAndIdNot(String razaoSocial, Long id);
    
    Optional<Empresa> findByTelefones_Id(Long telefoneId);

    Optional<Empresa> findByEndereco_Id(Long enderecoId);
}