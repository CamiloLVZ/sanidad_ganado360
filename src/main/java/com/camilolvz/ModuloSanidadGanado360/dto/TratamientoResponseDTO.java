package com.camilolvz.ModuloSanidadGanado360.dto;

import java.util.Date;
import java.util.UUID;

public class TratamientoResponseDTO {

    private UUID id;
    private UUID idIndividuo;
    private UUID idFinca;

    private String tipoTratamiento;

    private String dosis;
    private String frecuenciaAplicacion;
    private Integer numeroAplicaciones;

    private String productoUsado;
    private String enfermedadObjetivo;

    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaProximaDosis;

    private String nombreResponsable;
    private String estado;
    private String observaciones;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdIndividuo() {
        return idIndividuo;
    }

    public void setIdIndividuo(UUID idIndividuo) {
        this.idIndividuo = idIndividuo;
    }

    public UUID getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(UUID idFinca) {
        this.idFinca = idFinca;
    }

    public String getTipoTratamiento() {
        return tipoTratamiento;
    }

    public void setTipoTratamiento(String tipoTratamiento) {
        this.tipoTratamiento = tipoTratamiento;
    }

    public String getProductoUsado() {
        return productoUsado;
    }

    public void setProductoUsado(String productoUsado) {
        this.productoUsado = productoUsado;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuenciaAplicacion() {
        return frecuenciaAplicacion;
    }

    public void setFrecuenciaAplicacion(String frecuenciaAplicacion) {
        this.frecuenciaAplicacion = frecuenciaAplicacion;
    }

    public Integer getNumeroAplicaciones() {
        return numeroAplicaciones;
    }

    public void setNumeroAplicaciones(Integer numeroAplicaciones) {
        this.numeroAplicaciones = numeroAplicaciones;
    }

    public String getEnfermedadObjetivo() {
        return enfermedadObjetivo;
    }

    public void setEnfermedadObjetivo(String enfermedadObjetivo) {
        this.enfermedadObjetivo = enfermedadObjetivo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
