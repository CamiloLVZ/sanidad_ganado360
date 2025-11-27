package com.camilolvz.ModuloSanidadGanado360.dto;

import java.util.UUID;

public class ProductoSanitarioResponseDTO {

    private UUID id;
    private String nombre;
    private String tipo;
    private String especie;

    public ProductoSanitarioResponseDTO() {}

    public ProductoSanitarioResponseDTO(UUID id, String nombre, String tipo, String especie) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.especie = especie;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
}

