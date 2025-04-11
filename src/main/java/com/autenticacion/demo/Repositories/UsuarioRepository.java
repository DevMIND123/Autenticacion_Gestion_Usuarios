package com.autenticacion.demo.Repositories;

import com.autenticacion.demo.Entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Modifying
    @Query("UPDATE Usuario u SET u.nombre = :nombre, u.email = :email WHERE u.id = :id")
    int actualizarUsuario(@Param("id") Long id, @Param("nombre") String nombre, @Param("email") String email);
}