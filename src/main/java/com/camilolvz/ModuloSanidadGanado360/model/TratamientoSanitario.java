package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity

public class TratamientoSanitario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idIndividuo;
    private String idFinca;

    private String tipoTratamiento;
    private String productoUsado;
    private String dosis;
    private String viaAdministracion;
    private String frecuenciaAplicacion;
    private Integer numeroAplicaciones;
    private String enfermedadObjetivo;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Temporal(TemporalType.DATE)
    private Date fechaProximaDosis;

    private String nombreResponsable;

    private String estado;

    @Column(length = 2000)
    private String observaciones;

    public TratamientoSanitario() {
    }

    public TratamientoSanitario(Long id, String idIndividuo, String idFinca, String tipoTratamiento, String productoUsado, String dosis, String viaAdministracion, String frecuenciaAplicacion, String enfermedadObjetivo, Integer numeroAplicaciones, Date fechaInicio, Date fechaFin, Date fechaProximaDosis, String nombreResponsable, String estado, String observaciones) {
        this.id = id;
        this.idIndividuo = idIndividuo;
        this.idFinca = idFinca;
        this.tipoTratamiento = tipoTratamiento;
        this.productoUsado = productoUsado;
        this.dosis = dosis;
        this.viaAdministracion = viaAdministracion;
        this.frecuenciaAplicacion = frecuenciaAplicacion;
        this.enfermedadObjetivo = enfermedadObjetivo;
        this.numeroAplicaciones = numeroAplicaciones;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaProximaDosis = fechaProximaDosis;
        this.nombreResponsable = nombreResponsable;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(String idFinca) {
        this.idFinca = idFinca;
    }

    public String getIdIndividuo() {
        return idIndividuo;
    }

    public void setIdIndividuo(String idIndividuo) {
        this.idIndividuo = idIndividuo;
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

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
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

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
