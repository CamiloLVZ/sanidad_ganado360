package com.camilolvz.ModuloSanidadGanado360.mapper;


import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaEnfermedadRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaEnfermedadResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaEnfermedad;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaTratamiento;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper centralizado para IncidenciaEnfermedad.
 * - convierte RequestDTO + Enfermedad(resuelta) -> Entidad (sin asociar tratamientos)
 * - convierte Entidad -> ResponseDTO puro
 * - actualiza entidad existente desde RequestDTO (sin reasignar tratamientos)
 *
 * Nota: la resolución/creación de tratamientos se hace en el Service (repositorios).
 */
@Component
public class IncidenciaEnfermedadMapper {

    public IncidenciaEnfermedad toEntity(IncidenciaEnfermedadRequestDTO req, Enfermedad enf) {
        if (req == null) return null;
        IncidenciaEnfermedad e = new IncidenciaEnfermedad();
        e.setEnfermedad(enf);
        e.setIdAnimal(req.getIdAnimal());
        e.setResponsable(req.getResponsable());
        e.setFechaDiagnostico(req.getFechaDiagnostico());
        // no asigna tratamientos aquí (service lo hará)
        return e;
    }

    public IncidenciaEnfermedadResponseDTO toDto(IncidenciaEnfermedad e) {
        if (e == null) return null;

        List<UUID> tratamientoIds = (e.getTratamientos() == null) ? Collections.emptyList()
                : e.getTratamientos().stream()
                .map(IncidenciaTratamiento::getId)
                .collect(Collectors.toList());

        UUID enfermedadId = e.getEnfermedad() != null ? e.getEnfermedad().getId() : null;
        String enfermedadNombre = e.getEnfermedad() != null ? e.getEnfermedad().getNombre() : null;

        return new IncidenciaEnfermedadResponseDTO(
                e.getId(),
                e.getIdAnimal(),
                enfermedadId,
                enfermedadNombre,
                e.getResponsable(),
                e.getFechaDiagnostico(),
                tratamientoIds
        );
    }

    /**
     * Actualiza entidad existente con campos del DTO (parcial).
     * No reasigna tratamientos (el service se encarga de eso si req.getTratamientoIds()!=null).
     */
    public void updateEntityFromDto(IncidenciaEnfermedad existente, IncidenciaEnfermedadRequestDTO req, Enfermedad enf) {
        if (existente == null || req == null) return;

        if (enf != null) existente.setEnfermedad(enf);
        if (req.getIdAnimal() != null) existente.setIdAnimal(req.getIdAnimal());
        if (req.getResponsable() != null && !req.getResponsable().isBlank()) existente.setResponsable(req.getResponsable());
        if (req.getFechaDiagnostico() != null) existente.setFechaDiagnostico(req.getFechaDiagnostico());
        // tratamientos gestionados por el service
    }
}

