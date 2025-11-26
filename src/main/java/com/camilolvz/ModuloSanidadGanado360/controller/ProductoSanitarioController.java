package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import com.camilolvz.ModuloSanidadGanado360.model.ProductoSanitario;
import com.camilolvz.ModuloSanidadGanado360.service.ProductoSanitarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productos-sanitarios")
public class ProductoSanitarioController {

    private final ProductoSanitarioService service;
    public ProductoSanitarioController(ProductoSanitarioService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<ProductoSanitario> listarProductos() {
        return service.listarTodos();
    }
}
