package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.Rol;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaRespuestaDTO {
    private Long id;
    private String nombreEmpresa;
    private String nit;
    private String nombreRepresentante;
    private String email;
    private String direccion;
    private String telefono;
    private String estadoCuenta;
    private Rol rol;
}
