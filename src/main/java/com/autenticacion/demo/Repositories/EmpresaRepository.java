package com.autenticacion.demo.Repositories;

import com.autenticacion.demo.Entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByEmail(String email);
    Optional<Empresa> findByNit(String nit); 
}
