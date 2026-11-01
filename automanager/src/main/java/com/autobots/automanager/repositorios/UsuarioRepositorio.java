package com.autobots.automanager.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where :perfil member of u.perfis")
    List<Usuario> findByPerfil(@Param("perfil") PerfilUsuario perfil);
    boolean existsByCredenciais_Id(Long id);

    boolean existsByMercadorias_Id(Long id);

    Optional<Usuario> findByTelefones_Id(Long telefoneId);

    Optional<Usuario> findByEndereco_Id(Long enderecoId);

    Optional<Usuario> findByDocumentos_Id(Long documentoId);

    Optional<Usuario> findByEmails_Id(Long emailId);

    Optional<Usuario> findByCredenciais_Id(Long credencialId);

    Optional<Usuario> findByMercadorias_Id(Long mercadoriaId);
}
