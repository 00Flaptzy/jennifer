package com.epiis.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.epiis.app.entity.PedidoItem;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, String> {
	List<PedidoItem> findByPedido_IdPedido(String idPedido);
    
}
