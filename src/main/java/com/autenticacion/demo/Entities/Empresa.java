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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento", unique = true)
    private String numeroDocumento;

    @Column(name = "nombre_empresa")
    private String nombreEmpresa;

    @Column(name = "nombre_representante")
    private String nombreRepresentante;

    @Column(unique = true)
    private String email;

    private String password;

    private String estadoCuenta;

    @Column(name = "url_logo")
    private String urlLogo; // aquí guardaremos la URL de la imagen subida
}
