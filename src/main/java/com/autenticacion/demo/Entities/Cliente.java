package com.autenticacion.demo.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    private String estadoCuenta;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}
