package com.camilolvz.ModuloSanidadGanado360.mapper;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.EstadoIncidencia;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaTratamiento;
import com.camilolvz.ModuloSanidadGanado360.model.TratamientoSanitario;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncidenciaTratamientoMapper {

    /**
     * Convierte entidad → DTO
     */
    public static IncidenciaTratamientoResponseDTO toDTO(IncidenciaTratamiento e) {
        return IncidenciaTratamientoResponseDTO.fromEntity(e);
    }

    /**
     * Convierte DTO → Entidad (sin setear tratamiento aún)
     * El tratamiento se inyectará desde el Service.
     */
    public static IncidenciaTratamiento toEntity(IncidenciaTratamientoRequestDTO req) {
        IncidenciaTratamiento i = new IncidenciaTratamiento();

        i.setIdAnimal(req.getIdAnimal());
        i.setResponsable(req.getResponsable());
        i.setFechaTratamiento(req.getFechaTratamiento());

        if (req.getEstado() != null && !req.getEstado().isBlank()) {
            try {
                i.setEstado(EstadoIncidencia.valueOf(req.getEstado().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Estado inválido. Valores permitidos: PENDIENTE, REALIZADO, ANULADO"
                );
            }
        } else {
            i.setEstado(EstadoIncidencia.PENDIENTE);
        }

        return i;
    }
}
