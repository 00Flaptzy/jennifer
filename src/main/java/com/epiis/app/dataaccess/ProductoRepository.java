package com.epiis.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epiis.app.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, String> {
	
	 List<Producto> findByCategoria_IdCategoria(String idCategoria);

	 }

