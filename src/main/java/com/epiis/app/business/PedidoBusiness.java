package com.epiis.app.business;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.epiis.app.dataaccess.EmpleadoRepository;
import com.epiis.app.dataaccess.PedidoRepository;
import com.epiis.app.dataaccess.PedidoItemRepository;
import com.epiis.app.dto.DtoPedido;
import com.epiis.app.dto.DtoPedidoDetalle;
import com.epiis.app.dto.DtoPedidoItemDetalle;
import com.epiis.app.entity.Empleado;
import com.epiis.app.entity.Pedido;
import com.epiis.app.entity.PedidoItem;

@Service
public class PedidoBusiness {

    private final PedidoRepository pedidoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PedidoItemRepository pedidoItemRepository;

    public PedidoBusiness(PedidoRepository pedidoRepository,
                          EmpleadoRepository empleadoRepository,
                          PedidoItemRepository pedidoItemRepository) {
        this.pedidoRepository = pedidoRepository;
        this.empleadoRepository = empleadoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
    }

    // 🔹 INSERTAR PEDIDO
    public DtoPedido insert(DtoPedido dto) {

        Timestamp now = new Timestamp(System.currentTimeMillis());

        Empleado empleado = empleadoRepository.findById(
                "11111111-1111-1111-1111-111111111111")
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setIdPedido(UUID.randomUUID().toString());
        pedido.setEmpleado(empleado);
        pedido.setNombreCliente(dto.getNombreCliente());
        pedido.setTipoPedido(Pedido.TipoPedido.valueOf(dto.getTipoPedido().toUpperCase()));
        pedido.setMetodoPago(Pedido.MetodoPago.valueOf(dto.getMetodoPago().toUpperCase()));

        if (pedido.getTipoPedido() == Pedido.TipoPedido.MESA) {
            pedido.setMesa(dto.getMesa());
        }

        pedido.setEstado(Pedido.EstadoPedido.ESPERA);
        pedido.setTotalPagar(dto.getTotalPagar().doubleValue());
        pedido.setCreatedAt(now);
        pedido.setUpdatedAt(now);

        pedidoRepository.save(pedido);

        dto.setIdPedido(pedido.getIdPedido());
        dto.setEstado(pedido.getEstado().name());

        return dto;
    }

    // 🔹 LISTAR TODOS LOS PEDIDOS
    public List<DtoPedido> getAll() {
        List<DtoPedido> lista = new ArrayList<>();

        for (Pedido p : pedidoRepository.findAll()) {
            DtoPedido dto = new DtoPedido();
            dto.setIdPedido(p.getIdPedido());
            dto.setNombreCliente(p.getNombreCliente());
            dto.setTipoPedido(p.getTipoPedido().name());
            dto.setMetodoPago(p.getMetodoPago().name());
            dto.setEstado(p.getEstado().name());
            dto.setMesa(p.getMesa());
            dto.setTotalPagar(java.math.BigDecimal.valueOf(p.getTotalPagar()));
            lista.add(dto);
        }
        return lista;
    }

    // 🔹 OBTENER ESTADO POR ID (CLIENTE)
    public String getEstadoPorId(String idPedido) {
        return pedidoRepository.obtenerEstadoPorId(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"))
                .name();
    }

    // 🔹 CAMBIAR ESTADO (ADMIN)
    public void cambiarEstado(String idPedido, String nuevoEstado) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.setEstado(Pedido.EstadoPedido.valueOf(nuevoEstado.toUpperCase()));
        pedido.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        pedidoRepository.save(pedido);
    }

    // 🔹 OBTENER PEDIDO CON DETALLE DE ITEMS
    public DtoPedidoDetalle getPedidoConDetalle(String idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        DtoPedidoDetalle detalle = new DtoPedidoDetalle();
        detalle.setIdPedido(pedido.getIdPedido());
        detalle.setNombreCliente(pedido.getNombreCliente());
        detalle.setEstado(pedido.getEstado().name());
        detalle.setTipoPedido(pedido.getTipoPedido().name());
        detalle.setMetodoPago(pedido.getMetodoPago().name());
        detalle.setMesa(pedido.getMesa());
        detalle.setTotalPagar(java.math.BigDecimal.valueOf(pedido.getTotalPagar()));

        // 🔹 Listar items del pedido usando el método corregido
        List<PedidoItem> items = pedidoItemRepository.findByPedido_IdPedido(idPedido);
        List<DtoPedidoItemDetalle> itemsDto = new ArrayList<>();
        for (PedidoItem pi : items) {
            DtoPedidoItemDetalle itemDto = new DtoPedidoItemDetalle();
            itemDto.setIdItem(pi.getIdItem());
            itemDto.setNombreProducto(pi.getProducto().getNombre());
            itemDto.setCantidad(pi.getCantidad());
            itemDto.setPrecioUnitarioFinal(pi.getPrecioUnitarioFinal());
            itemsDto.add(itemDto);
        }
        detalle.setItems(itemsDto);

        return detalle;
    }
}
