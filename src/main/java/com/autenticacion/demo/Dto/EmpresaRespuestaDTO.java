package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.TipoDocumento;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaRespuestaDTO {
    private Long id;

    @NotNull(message = "El tipo de documento no puede ser nulo")
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El número de documento no puede ser nulo")
    private String numeroDocumento;

    @NotBlank(message = "El nombre de la empresa no puede ser nulo")
    private String nombreEmpresa;

    @NotBlank(message = "El nombre del representante no puede ser nulo")
    private String nombreRepresentante;

    @NotBlank(message = "El email no puede ser nulo")
    @Email(message = "El correo no tiene un formato válido")
    private String email;

    @NotBlank(message = "El estado de la cuenta no puede ser nulo")
    private String estadoCuenta;

    @NotBlank(message = "La URL del logo no puede ser nulo")
    private String urlLogo;
}
