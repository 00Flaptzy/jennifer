package com.epiis.app.dto;

import com.epiis.app.generic.DtoGeneric;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoEmpleado extends DtoGeneric {
    private String idEmpleado;
    private String nombre;
    private String password;
}
