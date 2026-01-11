package com.epiis.app.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.epiis.app.dataaccess.ProductoRepository;
import com.epiis.app.dataaccess.CategoriaRepository;
import com.epiis.app.dto.DtoProducto;
import com.epiis.app.entity.Producto;
import com.epiis.app.entity.Categoria;

@Service
public class ProductoBusiness {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoBusiness(ProductoRepository productoRepository,
                            CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public DtoProducto insert(DtoProducto dto) {

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setIdProducto(UUID.randomUUID().toString());
        producto.setCategoria(categoria);
        producto.setNombre(dto.getNombre());
        producto.setDisponible(dto.getDisponible());
        producto.setPrecioBase(dto.getPrecioBase());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        producto.setUpdatedAt(producto.getCreatedAt());

        productoRepository.save(producto);

        dto.setIdProducto(producto.getIdProducto());
        return dto;
    }

    // 🔥 ESTE ES EL MÉTODO QUE TU FRONT NECESITA
    public List<DtoProducto> getByCategoria(String idCategoria) {

        List<Producto> productos =
                productoRepository.findByCategoria_IdCategoria(idCategoria);

        List<DtoProducto> dtos = new ArrayList<>();

        for (Producto p : productos) {
            DtoProducto dto = new DtoProducto();
            dto.setIdProducto(p.getIdProducto());
            dto.setIdCategoria(p.getCategoria().getIdCategoria());
            dto.setNombre(p.getNombre());
            dto.setDisponible(p.getDisponible());
            dto.setPrecioBase(p.getPrecioBase());
            dto.setDescripcion(p.getDescripcion());
            dto.setCreatedAt(new Date(p.getCreatedAt().getTime()));
            dto.setUpdatedAt(new Date(p.getUpdatedAt().getTime()));
            dtos.add(dto);
        }

        return dtos;
    }
}

