package com.autenticacion.demo.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteActualizarDTO {

    @NotBlank
    @Email
    private String email;
}
