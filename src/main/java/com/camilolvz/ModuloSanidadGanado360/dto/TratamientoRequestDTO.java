package com.camilolvz.ModuloSanidadGanado360.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;


public class TratamientoRequestDTO {

    @NotBlank
    private UUID idIndividuo;

    @NotBlank
    private UUID idFinca;

    @NotBlank
    private String tipoTratamiento;

    @NotBlank
    private String productoUsado;

    @NotBlank
    private String dosis;

    private String viaAdministracion;
    private String frecuenciaAplicacion;
    private Integer numeroAplicaciones;
    private String enfermedadObjetivo;

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

    private Integer diasParaRefuerzo;


    public TratamientoRequestDTO() {
    }

    public TratamientoRequestDTO(UUID idIndividuo, UUID idFinca, String tipoTratamiento, String productoUsado, String dosis, String viaAdministracion, Integer numeroAplicaciones, String frecuenciaAplicacion, String enfermedadObjetivo, Date fechaInicio, Date fechaFin, Date fechaProximaDosis, String nombreResponsable, String estado, String observaciones) {
        this.idIndividuo = idIndividuo;
        this.idFinca = idFinca;
        this.tipoTratamiento = tipoTratamiento;
        this.productoUsado = productoUsado;
        this.dosis = dosis;
        this.viaAdministracion = viaAdministracion;
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

    public @NotBlank UUID getIdIndividuo() {
        return idIndividuo;
    }

    public void setIdIndividuo(@NotBlank UUID idIndividuo) {
        this.idIndividuo = idIndividuo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public @NotBlank String getEstado() {
        return estado;
    }

    public void setEstado(@NotBlank String estado) {
        this.estado = estado;
    }

    public @NotBlank String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(@NotBlank String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public Date getFechaProximaDosis() {
        return fechaProximaDosis;
    }

    public void setFechaProximaDosis(Date fechaProximaDosis) {
        this.fechaProximaDosis = fechaProximaDosis;
    }

    public @NotNull Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(@NotNull Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public @NotNull Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(@NotNull Date fechaInicio) {
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

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public @NotBlank String getDosis() {
        return dosis;
    }

    public void setDosis(@NotBlank String dosis) {
        this.dosis = dosis;
    }

    public @NotBlank String getProductoUsado() {
        return productoUsado;
    }

    public void setProductoUsado(@NotBlank String productoUsado) {
        this.productoUsado = productoUsado;
    }

    public @NotBlank String getTipoTratamiento() {
        return tipoTratamiento;
    }

    public void setTipoTratamiento(@NotBlank String tipoTratamiento) {
        this.tipoTratamiento = tipoTratamiento;
    }

    public @NotBlank UUID getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(@NotBlank UUID idFinca) {
        this.idFinca = idFinca;
    }
}
