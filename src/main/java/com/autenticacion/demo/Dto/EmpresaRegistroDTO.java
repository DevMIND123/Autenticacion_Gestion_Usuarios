package com.autenticacion.demo.Dto;

import com.autenticacion.demo.Entities.TipoDocumento;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaRegistroDTO {

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

    @NotBlank(message = "La contraseña no puede ser nulo")
    private String password;
}
