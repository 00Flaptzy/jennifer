package com.epiis.app.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.epiis.app.business.PedidoItemBusiness;
import com.epiis.app.dto.DtoPedidoItem;

@RestController
@RequestMapping("/pedidoItem")
public class PedidoItemController {

    @Autowired
    private PedidoItemBusiness pedidoItemBusiness;

    // 🔹 Insertar items de pedido desde FormData
    @PostMapping(value = "/insert/{idPedido}", consumes = {"multipart/form-data"})
    public ResponseEntity<Object> insert(
            @PathVariable("idPedido") String idPedido,
            @RequestParam("dto.pedidoItem.idProducto") String idProducto,
            @RequestParam("dto.pedidoItem.cantidad") Integer cantidad,
            @RequestParam("dto.pedidoItem.precioUnitarioFinal") String precioUnitarioStr
    ) {
        try {
            DtoPedidoItem dto = new DtoPedidoItem();
            dto.setIdPedido(idPedido);
            dto.setIdProducto(idProducto);
            dto.setCantidad(cantidad);
            dto.setPrecioUnitarioFinal(new BigDecimal(precioUnitarioStr));

            pedidoItemBusiness.insert(dto);

            // 🔹 Devuelve JSON para que Angular no falle
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Item agregado al pedido correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
}
