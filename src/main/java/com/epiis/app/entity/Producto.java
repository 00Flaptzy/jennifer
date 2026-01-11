package com.epiis.app.entity;

import com.epiis.app.generic.EntityGeneric;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "producto")
@Getter
@Setter
public class Producto extends EntityGeneric {

    @Id
    @Column(name = "idProducto")
    private String idProducto;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precioBase")
    private Double precioBase;

    @Column(name = "descripcion")
    private String descripcion;
}
