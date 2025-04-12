package com.autenticacion.demo.Entities;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import lombok.*;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "administradores")
@SuperBuilder

public class Administrador extends Persona {

    @Enumerated(EnumType.STRING)
    private Rol rol;

}
