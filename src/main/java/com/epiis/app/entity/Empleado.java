package com.epiis.app.entity;

import com.epiis.app.generic.EntityGeneric;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "empleado")
@Getter
@Setter
public class Empleado extends EntityGeneric {

    @Id
    @Column(name = "idEmpleado")
    private String idEmpleado;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "password")
    private String password;
}

