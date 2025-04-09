package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.TipoDocumento;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaRespuestaDTO {
    private Long id;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String nombreEmpresa;
    private String nombreRepresentante;
    private String email;
    private String estadoCuenta;
    private String urlLogo;
}
