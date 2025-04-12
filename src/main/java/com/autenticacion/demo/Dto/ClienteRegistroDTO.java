package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.Rol;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRegistroDTO {

    @NotBlank
    private String nombre;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Rol rol; // CLIENTE
}
