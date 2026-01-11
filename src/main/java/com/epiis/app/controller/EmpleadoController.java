package com.epiis.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.epiis.app.business.EmpleadoBusiness;
import com.epiis.app.controller.reqresp.RequestEmpleadoInsert;
import com.epiis.app.controller.reqresp.ResponseEmpleadoInsert;
import com.epiis.app.dto.DtoEmpleado;
import com.epiis.app.entity.Empleado;
import com.epiis.app.dataaccess.EmpleadoRepository;
import com.epiis.app.JwtService;

import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoBusiness empleadoBusiness;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private JwtService jwtService;

    // 🔹 Insertar empleado
    @PostMapping(path = "insert", consumes = "application/json")
    public ResponseEntity<ResponseEmpleadoInsert> insert(
            @RequestBody RequestEmpleadoInsert request) {

        ResponseEmpleadoInsert response = new ResponseEmpleadoInsert();
        empleadoBusiness.insert(request.getDto());
        response.success();
        response.getListMessage().add("Empleado registrado correctamente");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 🔹 Obtener todos los empleados
    @GetMapping(path = "getall")
    public ResponseEntity<List<DtoEmpleado>> getAll() {
        return new ResponseEntity<>(empleadoBusiness.getAll(), HttpStatus.OK);
    }

    // 🔹 Login
    @PostMapping("/login")
    public ResponseEntity<?> login(MultipartHttpServletRequest request) {
        // Obtenemos datos del FormData enviado desde Angular
        String nombre = request.getParameter("dto.dtoUser.nombre");
        String password = request.getParameter("dto.dtoUser.password");

        if (nombre == null || password == null) {
            return ResponseEntity.badRequest().body("Faltan parámetros");
        }

        // Buscar empleado en la base de datos
        Empleado empleado = empleadoRepository.findByNombre(nombre);
        if (empleado == null || !empleado.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        // Generar token JWT
        String token = jwtService.generateToken(nombre);

        // Devolver token
        return ResponseEntity.ok(Map.of("token", token));
    }
}
