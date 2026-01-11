package com.epiis.app.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import com.epiis.app.entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
	Empleado findByNombre(String nombre);
}
