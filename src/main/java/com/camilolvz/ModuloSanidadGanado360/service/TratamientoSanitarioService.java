package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.TratamientoSanitario;
import com.camilolvz.ModuloSanidadGanado360.repository.TratamientoSanitarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TratamientoSanitarioService {

    private final TratamientoSanitarioRepository repository;

    public TratamientoSanitarioService(TratamientoSanitarioRepository repository) {
        this.repository = repository;
    }


    // Conversión DTO → Entity
    private TratamientoSanitario convertirAEntidad(TratamientoRequestDTO dto) {
        TratamientoSanitario t = new TratamientoSanitario();
        t.setIdIndividuo(dto.getIdIndividuo());
        t.setIdFinca(dto.getIdFinca());
        t.setTipoTratamiento(dto.getTipoTratamiento());
        t.setProductoUsado(dto.getProductoUsado());
        t.setDosis(dto.getDosis());
        t.setViaAdministracion(dto.getViaAdministracion());
        t.setFrecuenciaAplicacion(dto.getFrecuenciaAplicacion());
        t.setNumeroAplicaciones(dto.getNumeroAplicaciones());
        t.setEnfermedadObjetivo(dto.getEnfermedadObjetivo());
        t.setFechaInicio(dto.getFechaInicio());
        t.setFechaFin(dto.getFechaFin());
        t.setFechaProximaDosis(dto.getFechaProximaDosis());
        t.setNombreResponsable(dto.getNombreResponsable());
        t.setEstado(dto.getEstado());
        t.setObservaciones(dto.getObservaciones());
        return t;
    }

    // Conversión Entity → DTO
    private TratamientoResponseDTO convertirADTO(TratamientoSanitario t) {
        TratamientoResponseDTO dto = new TratamientoResponseDTO();
        dto.setId(t.getId());
        dto.setIdIndividuo(t.getIdIndividuo());
        dto.setIdFinca(t.getIdFinca());
        dto.setTipoTratamiento(t.getTipoTratamiento());
        dto.setProductoUsado(t.getProductoUsado());
        dto.setDosis(t.getDosis());
        dto.setViaAdministracion(t.getViaAdministracion());
        dto.setFrecuenciaAplicacion(t.getFrecuenciaAplicacion());
        dto.setNumeroAplicaciones(t.getNumeroAplicaciones());
        dto.setEnfermedadObjetivo(t.getEnfermedadObjetivo());
        dto.setFechaInicio(t.getFechaInicio());
        dto.setFechaFin(t.getFechaFin());
        dto.setFechaProximaDosis(t.getFechaProximaDosis());
        dto.setNombreResponsable(t.getNombreResponsable());
        dto.setEstado(t.getEstado());
        dto.setObservaciones(t.getObservaciones());
        return dto;
    }

    private void validarFechas(TratamientoRequestDTO dto) {
        if (dto.getFechaFin().before(dto.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
    }

    public TratamientoResponseDTO crear(TratamientoRequestDTO dto) {
        validarFechas(dto);
        TratamientoSanitario entity = convertirAEntidad(dto);
        return convertirADTO(repository.save(entity));
    }

    public TratamientoResponseDTO actualizar(Long id, TratamientoRequestDTO dto) {
        validarFechas(dto);

        TratamientoSanitario existente = repository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }

        // actualizar campos
        existente.setIdIndividuo(dto.getIdIndividuo());
        existente.setIdFinca(dto.getIdFinca());
        existente.setTipoTratamiento(dto.getTipoTratamiento());
        existente.setProductoUsado(dto.getProductoUsado());
        existente.setDosis(dto.getDosis());
        existente.setViaAdministracion(dto.getViaAdministracion());
        existente.setFrecuenciaAplicacion(dto.getFrecuenciaAplicacion());
        existente.setNumeroAplicaciones(dto.getNumeroAplicaciones());
        existente.setEnfermedadObjetivo(dto.getEnfermedadObjetivo());
        existente.setFechaInicio(dto.getFechaInicio());
        existente.setFechaFin(dto.getFechaFin());
        existente.setFechaProximaDosis(dto.getFechaProximaDosis());
        existente.setNombreResponsable(dto.getNombreResponsable());
        existente.setEstado(dto.getEstado());
        existente.setObservaciones(dto.getObservaciones());

        return convertirADTO(repository.save(existente));
    }

    public TratamientoResponseDTO obtener(Long id) {
        TratamientoSanitario encontrado = repository.findById(id).orElse(null);
        return encontrado != null ? convertirADTO(encontrado) : null;
    }

    public List<TratamientoResponseDTO> obtenerPorIndividuo(String idIndividuo) {
        return repository.findByIdIndividuo(idIndividuo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<TratamientoResponseDTO> obtenerPorFinca(String idFinca) {
        return repository.findByIdFinca(idFinca)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
