package com.autenticacion.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioActualizarDTO {
    @NotBlank(message = "El nombre no puede ser nulo")
    private String nombre;

    @NotBlank(message = "El email no puede ser nulo")
    private String email;
}
