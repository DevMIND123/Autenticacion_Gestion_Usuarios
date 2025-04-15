package com.autenticacion.demo.Repositories;

import com.autenticacion.demo.Entities.Administrador;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    Optional<Administrador> findByEmail(String email);

    @Modifying
    @Query("UPDATE Administrador a SET a.nombre = :nombre, a.email = :email WHERE a.id = :id")
    int actualizarAdministrador(@Param("id") Long id, @Param("nombre") String nombre, @Param("email") String email);
}
