package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class ProductoSanitario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String nombre;

    public ProductoSanitario() {}

    public ProductoSanitario(String nombre) {
        this.nombre = nombre;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
