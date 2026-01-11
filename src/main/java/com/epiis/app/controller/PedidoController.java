package com.epiis.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.epiis.app.business.PedidoBusiness;
import com.epiis.app.dto.DtoPedido;
import com.epiis.app.dto.DtoPedidoDetalle;

@RestController
@RequestMapping("/pedido")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoController {

    private final PedidoBusiness pedidoBusiness;

    public PedidoController(PedidoBusiness pedidoBusiness) {
        this.pedidoBusiness = pedidoBusiness;
    }

    @PostMapping(value = "/insert", consumes = "multipart/form-data")
    public ResponseEntity<?> insert(
            @RequestParam String nombreCliente,
            @RequestParam String tipoPedido,
            @RequestParam String metodoPago,
            @RequestParam(required = false) Integer mesa,
            @RequestParam String totalPagar) {

        DtoPedido dto = new DtoPedido();
        dto.setNombreCliente(nombreCliente);
        dto.setTipoPedido(tipoPedido);
        dto.setMetodoPago(metodoPago);
        dto.setMesa(mesa);
        dto.setTotalPagar(new BigDecimal(totalPagar));

        DtoPedido result = pedidoBusiness.insert(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("idPedido", result.getIdPedido()));
    }

    @GetMapping("/getall")
    public List<DtoPedido> getAll() {
        return pedidoBusiness.getAll();
    }

    // 👀 CLIENTE
    @GetMapping("/estado/{idPedido}")
    public String getEstado(@PathVariable String idPedido) {
        return pedidoBusiness.getEstadoPorId(idPedido);
    }

    // 🔄 ADMIN
    @PutMapping("/estado/{idPedido}")
    public void cambiarEstado(
            @PathVariable String idPedido,
            @RequestBody Map<String, String> body) {

        pedidoBusiness.cambiarEstado(idPedido, body.get("estado"));
    }
    @GetMapping("/detalle/{idPedido}")
    public ResponseEntity<DtoPedidoDetalle> getDetallePedido(@PathVariable String idPedido) {
        DtoPedidoDetalle detalle = pedidoBusiness.getPedidoConDetalle(idPedido);
        return ResponseEntity.ok(detalle);
    }

}
