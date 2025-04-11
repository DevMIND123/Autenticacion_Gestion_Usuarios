package com.autenticacion.demo.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CambioPasswordDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nuevaPassword;
}
