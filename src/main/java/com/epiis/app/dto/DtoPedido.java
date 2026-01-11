package com.epiis.app.dto;

import com.epiis.app.generic.DtoGeneric;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class DtoPedido extends DtoGeneric {
    private String idPedido;
    private String idEmpleado;
    private String nombreCliente;
    private String estado;
    private BigDecimal totalPagar;
    private String tipoPedido;
    private String metodoPago;
    private Integer mesa;
    
}
