package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.Rol;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRespuestaDTO {
    private Long id;
    private String nombre;
    private String email;
    private String estadoCuenta;
    private Rol rol;
}
