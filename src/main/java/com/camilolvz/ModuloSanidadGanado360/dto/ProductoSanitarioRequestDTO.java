package com.camilolvz.ModuloSanidadGanado360.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductoSanitarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    /**
     * Tipo del producto (por ejemplo: "Vacuna", "Antibiótico", etc.)
     * Opcional: si no quieres obligarlo quita la anotación.
     */
    private String tipo;

    /**
     * Especie objetivo (por ejemplo: "Bovino", "Porcino"). Opcional.
     */
    private String especie;

    public ProductoSanitarioRequestDTO() {}

    public ProductoSanitarioRequestDTO(String nombre, String tipo, String especie) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.especie = especie;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
}
