package com.autenticacion.demo.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empresas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_empresa", nullable = false)
    private String nombreEmpresa;

    @Column(name = "nit", unique = true, nullable = false)
    private String nit;

    @Column(name = "nombre_representante", nullable = false)
    private String nombreRepresentante;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String password;

    private String estadoCuenta;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}
