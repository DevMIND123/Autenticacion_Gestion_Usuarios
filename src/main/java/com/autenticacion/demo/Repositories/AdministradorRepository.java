package com.autenticacion.demo.Repositories;

import com.autenticacion.demo.Entities.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByEmail(String email);

    @Modifying
    @Query("UPDATE Administrador a SET a.nombre = :nombre, a.email = :email WHERE a.id = :id")
    int actualizarAdministrador(@Param("id") Long id, @Param("nombre") String nombre, @Param("email") String email);
}