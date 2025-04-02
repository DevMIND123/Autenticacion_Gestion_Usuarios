package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.Rol;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRegistroDTO {
    private String nombre;
    private String email;
    private String password;
    private Rol rol;
}
