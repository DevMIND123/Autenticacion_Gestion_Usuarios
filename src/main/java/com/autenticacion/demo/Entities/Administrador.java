package com.autenticacion.demo.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "administradores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String estadoCuenta;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}
