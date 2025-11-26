package com.camilolvz.ModuloSanidadGanado360.repository;

import com.camilolvz.ModuloSanidadGanado360.model.ProductoSanitario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductoSanitarioRepository extends JpaRepository<ProductoSanitario, UUID> {
    ProductoSanitario findByNombreIgnoreCase(String nombre);
}

