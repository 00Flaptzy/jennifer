package com.epiis.app.dto;

import com.epiis.app.generic.DtoGeneric;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoProducto extends DtoGeneric {
    private String idProducto;
    private String idCategoria; 
    private Boolean disponible;
    private String nombre;
    private Double precioBase;
    private String descripcion;
}
