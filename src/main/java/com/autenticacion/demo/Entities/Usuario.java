package com.autenticacion.demo.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(name = "estado_cuenta")
    private String estadoCuenta;
}