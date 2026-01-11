package com.epiis.app.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epiis.app.dataaccess.PedidoItemRepository;
import com.epiis.app.dataaccess.PedidoRepository;
import com.epiis.app.dataaccess.ProductoRepository;
import com.epiis.app.dto.DtoPedidoItem;
import com.epiis.app.entity.Pedido;
import com.epiis.app.entity.PedidoItem;
import com.epiis.app.entity.Producto;

@Service
public class PedidoItemBusiness {

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public boolean insert(DtoPedidoItem dto) {

        Timestamp now = new Timestamp(System.currentTimeMillis());

        Producto producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Pedido pedido = pedidoRepository.findById(dto.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        PedidoItem item = new PedidoItem();
        item.setIdItem(UUID.randomUUID().toString());
        item.setProducto(producto);
        item.setPedido(pedido);
        item.setCantidad(dto.getCantidad());
        item.setPrecioUnitarioFinal(dto.getPrecioUnitarioFinal());
        item.setCreatedAt(now);
        item.setUpdatedAt(now);

        pedidoItemRepository.save(item);
        return true;
    }

    public List<DtoPedidoItem> getAll() {

        List<PedidoItem> items = pedidoItemRepository.findAll();
        List<DtoPedidoItem> dtos = new ArrayList<>();

        for (PedidoItem pi : items) {
            DtoPedidoItem dto = new DtoPedidoItem();
            dto.setIdItem(pi.getIdItem());
            dto.setIdProducto(pi.getProducto().getIdProducto());
            dto.setIdPedido(pi.getPedido().getIdPedido());
            dto.setCantidad(pi.getCantidad());
            dto.setPrecioUnitarioFinal(pi.getPrecioUnitarioFinal());
            dto.setCreatedAt(new Date(pi.getCreatedAt().getTime()));
            dto.setUpdatedAt(new Date(pi.getUpdatedAt().getTime()));
            dtos.add(dto);
        }

        return dtos;
    }
    
}
