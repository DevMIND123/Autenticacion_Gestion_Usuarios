package com.autenticacion.demo.Repositories;

import com.autenticacion.demo.Entities.Cliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    @Modifying
    @Query("UPDATE Cliente c SET c.nombre = :nombre, c.email = :email WHERE c.id = :id")
    int actualizarCliente(@Param("id") Long id, @Param("nombre") String nombre, @Param("email") String email);
}
