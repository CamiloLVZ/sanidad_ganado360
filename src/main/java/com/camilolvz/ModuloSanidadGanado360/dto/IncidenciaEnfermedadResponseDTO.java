package com.camilolvz.ModuloSanidadGanado360.dto;

import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaEnfermedad;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class IncidenciaEnfermedadResponseDTO {

    private UUID id;
    private UUID idAnimal;
    private UUID enfermedadId;
    private String enfermedadNombre;

    private String responsable;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaDiagnostico;

    private List<UUID> tratamientoIds;

    public IncidenciaEnfermedadResponseDTO() {}

    public static IncidenciaEnfermedadResponseDTO fromEntity(IncidenciaEnfermedad i) {
        IncidenciaEnfermedadResponseDTO dto = new IncidenciaEnfermedadResponseDTO();

        dto.id = i.getId();
        dto.idAnimal = i.getIdAnimal();
        dto.enfermedadId = i.getEnfermedad() != null ? i.getEnfermedad().getId() : null;
        dto.enfermedadNombre = i.getEnfermedad() != null ? i.getEnfermedad().getNombre() : null;
        dto.responsable = i.getResponsable();
        dto.fechaDiagnostico = i.getFechaDiagnostico();

        dto.tratamientoIds = (i.getTratamientos() == null) ? null :
                i.getTratamientos()
                        .stream()
                        .map(t -> t.getId())
                        .collect(Collectors.toList());

        return dto;
    }

    // Getters & setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdAnimal() { return idAnimal; }
    public void setIdAnimal(UUID idAnimal) { this.idAnimal = idAnimal; }

    public UUID getEnfermedadId() { return enfermedadId; }
    public void setEnfermedadId(UUID enfermedadId) { this.enfermedadId = enfermedadId; }

    public String getEnfermedadNombre() { return enfermedadNombre; }
    public void setEnfermedadNombre(String enfermedadNombre) { this.enfermedadNombre = enfermedadNombre; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaDiagnostico() { return fechaDiagnostico; }
    public void setFechaDiagnostico(Date fechaDiagnostico) { this.fechaDiagnostico = fechaDiagnostico; }

    public List<UUID> getTratamientoIds() { return tratamientoIds; }
    public void setTratamientoIds(List<UUID> tratamientoIds) { this.tratamientoIds = tratamientoIds; }
}
