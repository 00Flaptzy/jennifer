package com.epiis.app.dataaccess;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.epiis.app.dto.ProductoEstrellaDTO;
import com.epiis.app.dto.VentaSemanaDTO;
import com.epiis.app.entity.Pedido;

public interface EstadisticaRepository extends JpaRepository<Pedido, String> {

    // 💰 Total vendido HOY
    @Query(value = """
        SELECT SUM(p.totalPagar)
        FROM pedido p
        WHERE DATE(p.createdAt) = CURDATE()
    """, nativeQuery = true)
    Double totalVendidoHoy();

    // 📆 Ventas últimos 7 días
    @Query(value = """
        SELECT 
          DATE(p.createdAt) AS dia,
          SUM(p.totalPagar) AS total
        FROM pedido p
        WHERE p.createdAt >= CURDATE() - INTERVAL 6 DAY
        GROUP BY DATE(p.createdAt)
        ORDER BY DATE(p.createdAt)
    """, nativeQuery = true)
    List<VentaSemanaDTO> ventasSemana();

    // ⭐ Producto estrella por rango de fechas
    @Query(value = """
        SELECT 
          pr.nombre AS nombre,
          SUM(pi.cantidad) AS totalVendido
        FROM pedidoitem pi
        JOIN producto pr ON pr.idProducto = pi.idProducto
        JOIN pedido p ON p.idPedido = pi.idPedido
        WHERE p.createdAt BETWEEN 
              STR_TO_DATE(:inicio, '%Y-%m-%d') 
          AND STR_TO_DATE(:fin, '%Y-%m-%d')
        GROUP BY pr.nombre
        ORDER BY totalVendido DESC
    """, nativeQuery = true)
    List<ProductoEstrellaDTO> productoEstrella(
        @Param("inicio") String inicio,
        @Param("fin") String fin
    );
}


