package com.epiis.app.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.epiis.app.business.ProductoBusiness;
import com.epiis.app.controller.reqresp.RequestProductoInsert;
import com.epiis.app.dto.DtoProducto;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoBusiness productoBusiness;

    public ProductoController(ProductoBusiness productoBusiness) {
        this.productoBusiness = productoBusiness;
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<DtoProducto>> getByCategoria(
            @PathVariable String idCategoria) {

        return ResponseEntity.ok(
                productoBusiness.getByCategoria(idCategoria)
        );
    }
    @PostMapping
    public ResponseEntity<DtoProducto> insert(
            @RequestBody RequestProductoInsert request) {

        DtoProducto dto = productoBusiness.insert(request.getProducto());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}

