package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.Rol;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdministradorRegistroDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nombre;

    @NotNull
    private Rol rol; // ADMINISTRADOR / SOPORTE / MARKETING
}
