package com.epiis.app.entity;

import com.epiis.app.generic.EntityGeneric;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedido")
public class Pedido extends EntityGeneric {

    @Id
    @Column(name = "idPedido")
    private String idPedido;

    @ManyToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado empleado;
    
    @Column(name = "nombreCliente")
    private String nombreCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoPedido")
    private TipoPedido tipoPedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodoPago")
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPedido estado;

    @Column(name = "mesa")
    private Integer mesa;

    @Column(name = "totalPagar")
    private Double totalPagar;

    public enum TipoPedido {
        MESA,
        LLEVAR
    }

    public enum MetodoPago {
        EFECTIVO,
        YAPE
    }

    public enum EstadoPedido {
        ESPERA,
        PREPARACION,
        LISTO
    }
}
