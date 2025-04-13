package com.autenticacion.demo.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {
    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;
}
