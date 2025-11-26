package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "diagnostico")
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID idIndividuo;
    private UUID idFinca;

    @ManyToOne(optional = false)
    private Enfermedad enfermedad;

    private LocalDate fechaDiagnostico;

    private String responsable;

    @Column(length = 2000)
    private String observaciones;

    public Diagnostico( UUID idIndividuo, UUID idFinca, Enfermedad enfermedad, LocalDate fechaDiagnostico, String responsable, String observaciones) {
        this.idIndividuo = idIndividuo;
        this.idFinca = idFinca;
        this.enfermedad = enfermedad;
        this.fechaDiagnostico = fechaDiagnostico;
        this.responsable = responsable;
        this.observaciones = observaciones;
    }

    public Diagnostico() {
    }

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

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(Enfermedad enfermedad) {
        this.enfermedad = enfermedad;
    }

    public UUID getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(UUID idFinca) {
        this.idFinca = idFinca;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public LocalDate getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(LocalDate fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
