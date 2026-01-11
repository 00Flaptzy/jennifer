package com.epiis.app.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPedidoItemDetalle {
    private String idItem;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitarioFinal;
}
