package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.Rol;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthenticationResponse {
    private String token;
    private String email;
    private String nombre;
    private Rol rol;
}