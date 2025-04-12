package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.Rol;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaRegistroDTO {

    @NotBlank
    private String nombreEmpresa;

    @NotBlank
    private String nit;

    @NotBlank
    private String nombreRepresentante;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String direccion;

    @NotBlank
    private String telefono;

    @NotBlank
    private String password;

    @NotNull
    private Rol rol; // EMPRESA
}
