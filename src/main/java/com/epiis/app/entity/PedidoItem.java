package com.epiis.app.entity;

import java.math.BigDecimal;

import com.epiis.app.generic.EntityGeneric;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pedidoItem")
@Getter
@Setter
public class PedidoItem extends EntityGeneric {

    @Id
    @Column(name = "idItem")
    private String idItem;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precioUnitarioFinal")
    private BigDecimal precioUnitarioFinal;
}
