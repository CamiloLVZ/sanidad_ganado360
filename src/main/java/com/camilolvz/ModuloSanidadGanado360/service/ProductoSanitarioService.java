package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.model.ProductoSanitario;
import com.camilolvz.ModuloSanidadGanado360.repository.ProductoSanitarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoSanitarioService {

    private final ProductoSanitarioRepository repository;

    public ProductoSanitarioService(ProductoSanitarioRepository repository) {
        this.repository = repository;
    }

    public List<ProductoSanitario> listarTodos() {
        return repository.findAll();
    }

    public ProductoSanitario crear(String nombre) {
        ProductoSanitario p = new ProductoSanitario();
        p.setNombre(nombre);
        return repository.save(p);
    }

    public ProductoSanitario buscarPorNombre(String nombre) {
        return repository.findByNombreIgnoreCase(nombre);
    }


    // Devuelve el producto si existe, si no lo crea automáticamente.

    public ProductoSanitario obtenerOcrear(String nombre) {
        ProductoSanitario p = repository.findByNombreIgnoreCase(nombre);
        return p != null ? p : crear(nombre);
    }
}
