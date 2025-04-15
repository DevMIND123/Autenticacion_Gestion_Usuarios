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


    public JwtAuthenticationResponse(String token, String email, Rol rol, String nombre) {
        this.token = token;
        this.email = email;
        this.rol = rol;
        this.nombre = nombre;
    }
}