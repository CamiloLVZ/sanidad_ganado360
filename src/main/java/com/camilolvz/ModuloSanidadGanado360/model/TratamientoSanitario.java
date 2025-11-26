package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity

public class TratamientoSanitario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID idIndividuo;
    private UUID idFinca;

    private String tipoTratamiento; //aqui podemos guardar si es vacuna u otro tipo

    private String dosis;
    private String frecuenciaAplicacion;
    private Integer numeroAplicaciones;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private ProductoSanitario productoUsado;

    @ManyToOne(optional = true)
    private Enfermedad enfermedadObjetivo;

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

    public TratamientoSanitario(UUID idIndividuo, UUID idFinca, String tipoTratamiento, ProductoSanitario productoUsado, String dosis, String viaAdministracion, String frecuenciaAplicacion, Enfermedad enfermedadObjetivo, Integer numeroAplicaciones, Date fechaInicio, Date fechaFin, Date fechaProximaDosis, String nombreResponsable, String estado, String observaciones) {
        this.idIndividuo = idIndividuo;
        this.idFinca = idFinca;
        this.tipoTratamiento = tipoTratamiento;
        this.productoUsado = productoUsado;
        this.dosis = dosis;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(UUID idFinca) {
        this.idFinca = idFinca;
    }

    public UUID getIdIndividuo() {
        return idIndividuo;
    }

    public void setIdIndividuo(UUID idIndividuo) {
        this.idIndividuo = idIndividuo;
    }

    public String getTipoTratamiento() {
        return tipoTratamiento;
    }

    public void setTipoTratamiento(String tipoTratamiento) {
        this.tipoTratamiento = tipoTratamiento;
    }

    public ProductoSanitario getProductoUsado() {
        return productoUsado;
    }

    public void setProductoUsado(ProductoSanitario productoUsado) {
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

    public Enfermedad getEnfermedadObjetivo() {
        return enfermedadObjetivo;
    }

    public void setEnfermedadObjetivo(Enfermedad enfermedadObjetivo) {
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
