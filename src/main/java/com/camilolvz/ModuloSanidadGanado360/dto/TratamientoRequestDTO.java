package com.camilolvz.ModuloSanidadGanado360.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.util.Date;
import java.util.UUID;

public class TratamientoRequestDTO {

    @NotNull
    private UUID idIndividuo;

    @NotNull
    private UUID idFinca;

    @NotBlank
    private String tipoTratamiento;

    @NotBlank
    private String dosis;

    private String frecuenciaAplicacion;
    private Integer numeroAplicaciones;

    @NotBlank
    private String productoUsado; // nombre del producto

    private String enfermedadObjetivo; // nombre, opcional

    @NotNull
    private Date fechaInicio;

    @NotNull
    private Date fechaFin;

    private Date fechaProximaDosis;

    @NotBlank
    private String nombreResponsable;

    @NotBlank
    private String estado;

    private String observaciones;


    public TratamientoRequestDTO() {
    }

    public TratamientoRequestDTO(UUID idIndividuo, UUID idFinca, String tipoTratamiento, String productoUsado, String dosis, String viaAdministracion, Integer numeroAplicaciones, String frecuenciaAplicacion, String enfermedadObjetivo, Date fechaInicio, Date fechaFin, Date fechaProximaDosis, String nombreResponsable, String estado, String observaciones) {
        this.idIndividuo = idIndividuo;
        this.idFinca = idFinca;
        this.tipoTratamiento = tipoTratamiento;
        this.productoUsado = productoUsado;
        this.dosis = dosis;
        this.numeroAplicaciones = numeroAplicaciones;
        this.frecuenciaAplicacion = frecuenciaAplicacion;
        this.enfermedadObjetivo = enfermedadObjetivo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaProximaDosis = fechaProximaDosis;
        this.nombreResponsable = nombreResponsable;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public UUID getIdIndividuo() {
        return idIndividuo;
    }

    public void setIdIndividuo(UUID idIndividuo) {
        this.idIndividuo = idIndividuo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public Date getFechaProximaDosis() {
        return fechaProximaDosis;
    }

    public void setFechaProximaDosis(Date fechaProximaDosis) {
        this.fechaProximaDosis = fechaProximaDosis;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public @NotNull Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getEnfermedadObjetivo() {
        return enfermedadObjetivo;
    }

    public void setEnfermedadObjetivo(String enfermedadObjetivo) {
        this.enfermedadObjetivo = enfermedadObjetivo;
    }

    public Integer getNumeroAplicaciones() {
        return numeroAplicaciones;
    }

    public void setNumeroAplicaciones(Integer numeroAplicaciones) {
        this.numeroAplicaciones = numeroAplicaciones;
    }

    public String getFrecuenciaAplicacion() {
        return frecuenciaAplicacion;
    }

    public void setFrecuenciaAplicacion(String frecuenciaAplicacion) {
        this.frecuenciaAplicacion = frecuenciaAplicacion;
    }
    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getProductoUsado() {
        return productoUsado;
    }

    public void setProductoUsado(String productoUsado) {
        this.productoUsado = productoUsado;
    }

    public String getTipoTratamiento() {
        return tipoTratamiento;
    }

    public void setTipoTratamiento(String tipoTratamiento) {
        this.tipoTratamiento = tipoTratamiento;
    }

    public UUID getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(UUID idFinca) {
        this.idFinca = idFinca;
    }
}
