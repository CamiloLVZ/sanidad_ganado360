package com.camilolvz.ModuloSanidadGanado360.dto;

import java.time.LocalDate;
import java.util.UUID;

public class DiagnosticoRequestDTO {

    private UUID idIndividuo;
    private UUID idFinca;
    private String nombreEnfermedad;
    private LocalDate fechaDiagnostico;
    private String responsable;
    private String observaciones;

    public UUID getIdIndividuo() { return idIndividuo; }
    public void setIdIndividuo(UUID idIndividuo) { this.idIndividuo = idIndividuo; }

    public UUID getIdFinca() { return idFinca; }
    public void setIdFinca(UUID idFinca) { this.idFinca = idFinca; }

    public String getNombreEnfermedad() { return nombreEnfermedad; }
    public void setNombreEnfermedad(String nombreEnfermedad) { this.nombreEnfermedad = nombreEnfermedad; }

    public LocalDate getFechaDiagnostico() { return fechaDiagnostico; }
    public void setFechaDiagnostico(LocalDate fechaDiagnostico) { this.fechaDiagnostico = fechaDiagnostico; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
