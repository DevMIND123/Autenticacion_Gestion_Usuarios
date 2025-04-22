package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.Rol;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaRegistroDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String direccion;

    @NotBlank
    private String nombreEmpresa;

    @NotBlank
    private String nit;

    @NotBlank
    private String nombreRepresentante;

    @NotBlank
    private String telefono;

    @NotNull
    private Rol rol; // EMPRESA
}
