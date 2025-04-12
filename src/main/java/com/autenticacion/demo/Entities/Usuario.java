package com.autenticacion.demo.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor 
@Data
@SuperBuilder
@Entity
@Table(name = "usuarios")
public class Usuario extends Persona {

}
