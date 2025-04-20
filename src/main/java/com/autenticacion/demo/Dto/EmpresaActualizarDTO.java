package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.Rol;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaActualizarDTO {

    @NotBlank
    private String nombreEmpresa;

    @NotBlank
    private String nit;

    @NotBlank
    private String nombreRepresentante;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String direccion;

    @NotBlank
    private String telefono;

    @NotNull 
    private Rol rol;
}
