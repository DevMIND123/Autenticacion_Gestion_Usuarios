package com.autenticacion.demo.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CambioPasswordDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nuevaPassword;
}
