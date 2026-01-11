package com.epiis.app.dto;

import java.math.BigDecimal;
import com.epiis.app.generic.DtoGeneric;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPedidoItem extends DtoGeneric {
    private String idItem;
    private String idProducto; 
    private String idPedido;    
    private Integer cantidad;
    private BigDecimal precioUnitarioFinal;
}
