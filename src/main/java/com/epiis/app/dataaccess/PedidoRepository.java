package com.epiis.app.dataaccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epiis.app.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, String> {
	
	Pedido findTopByOrderByCreatedAtDesc();
	
	@Query("SELECT p.estado FROM Pedido p WHERE p.idPedido = :idPedido")
    Optional<Pedido.EstadoPedido> obtenerEstadoPorId(@Param("idPedido") String idPedido);
	
	
}
