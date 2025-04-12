package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.Rol;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdministradorRespuestaDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede ser nulo")
    private String nombre;

    @NotBlank(message = "El email no puede ser nulo")
    @Email(message = "El correo no tiene un formato válido")
    private String email;

    @NotNull(message = "Rol no puede ser nulo")
    private Rol rol;

    @NotBlank(message = "El estado de la cuenta no puede ser nula")
    private String estadoCuenta;
}
