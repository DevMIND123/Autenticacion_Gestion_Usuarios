package com.autenticacion.demo.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioActualizarDTO {
    private String nombre;
    private String email;
}
