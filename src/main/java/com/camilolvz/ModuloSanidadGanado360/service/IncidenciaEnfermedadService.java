package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaEnfermedadRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaEnfermedadResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.mapper.IncidenciaEnfermedadMapper;
import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaEnfermedad;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaTratamiento;
import com.camilolvz.ModuloSanidadGanado360.repository.EnfermedadRepository;
import com.camilolvz.ModuloSanidadGanado360.repository.IncidenciaEnfermedadRepository;
import com.camilolvz.ModuloSanidadGanado360.repository.IncidenciaTratamientoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IncidenciaEnfermedadService {

    private final IncidenciaEnfermedadRepository repository;
    private final EnfermedadRepository enfermedadRepository;
    private final IncidenciaTratamientoRepository tratamientoRepository;
    private final IncidenciaEnfermedadMapper mapper;

    public IncidenciaEnfermedadService(
            IncidenciaEnfermedadRepository repository,
            EnfermedadRepository enfermedadRepository,
            IncidenciaTratamientoRepository tratamientoRepository,
            IncidenciaEnfermedadMapper mapper
    ) {
        this.repository = repository;
        this.enfermedadRepository = enfermedadRepository;
        this.tratamientoRepository = tratamientoRepository;
        this.mapper = mapper;
    }

    private IncidenciaEnfermedadResponseDTO mapToDTO(IncidenciaEnfermedad e) {
        return mapper.toDto(e);
    }

    private IncidenciaEnfermedad convertirAEntidad(IncidenciaEnfermedadRequestDTO req) {

        Enfermedad enf = enfermedadRepository.findById(req.getEnfermedadId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Enfermedad no encontrada con id: " + req.getEnfermedadId()));

        return mapper.toEntity(req, enf);
    }

    public IncidenciaEnfermedadResponseDTO crear(IncidenciaEnfermedadRequestDTO req) {

        IncidenciaEnfermedad entidad = convertirAEntidad(req);
        IncidenciaEnfermedad guardada = repository.save(entidad);

        // Asociar tratamientos (si vienen IDs)
        if (req.getTratamientoIds() != null && !req.getTratamientoIds().isEmpty()) {
            List<IncidenciaTratamiento> tratamientos = req.getTratamientoIds().stream()
                    .map(tid -> tratamientoRepository.findById(tid)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "Tratamiento no encontrado: " + tid)))
                    .peek(t -> t.setIncidenciaEnfermedad(guardada))
                    .collect(Collectors.toList());

            tratamientoRepository.saveAll(tratamientos);
            guardada.setTratamientos(tratamientos);
        }

        return mapToDTO(guardada);
    }

    public List<IncidenciaEnfermedadResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    public IncidenciaEnfermedadResponseDTO obtener(UUID id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));
    }

    public IncidenciaEnfermedadResponseDTO actualizar(UUID id, IncidenciaEnfermedadRequestDTO req) {

        IncidenciaEnfermedad existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Enfermedad enf = null;
        if (req.getEnfermedadId() != null) {
            enf = enfermedadRepository.findById(req.getEnfermedadId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Enfermedad no encontrada con id: " + req.getEnfermedadId()));
        }

        // Actualizar campos básicos (sin tocar tratamientos)
        mapper.updateEntityFromDto(existente, req, enf);

        // Tratamientos: reasignar completamente si vienen IDs
        if (req.getTratamientoIds() != null) {

            // Quitar asociación previa
            if (existente.getTratamientos() != null && !existente.getTratamientos().isEmpty()) {
                existente.getTratamientos().forEach(t -> t.setIncidenciaEnfermedad(null));
                tratamientoRepository.saveAll(existente.getTratamientos());
            }

            // Asociar nueva lista
            List<IncidenciaTratamiento> nuevos = req.getTratamientoIds().stream()
                    .map(tid -> tratamientoRepository.findById(tid)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "Tratamiento no encontrado: " + tid)))
                    .peek(t -> t.setIncidenciaEnfermedad(existente))
                    .collect(Collectors.toList());

            tratamientoRepository.saveAll(nuevos);
            existente.setTratamientos(nuevos);
        }

        IncidenciaEnfermedad saved = repository.save(existente);
        return mapToDTO(saved);
    }

    public void eliminar(UUID id) {
        IncidenciaEnfermedad existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Desasociar tratamientos
        if (existente.getTratamientos() != null && !existente.getTratamientos().isEmpty()) {
            existente.getTratamientos().forEach(t -> t.setIncidenciaEnfermedad(null));
            tratamientoRepository.saveAll(existente.getTratamientos());
        }

        repository.delete(existente);
    }

    public List<IncidenciaEnfermedadResponseDTO> obtenerPorAnimal(UUID idAnimal) {

        List<IncidenciaEnfermedad> lista = repository.findByIdAnimal(idAnimal);

        if (lista.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No hay incidencias para el animal con id: " + idAnimal);
        }

        return lista.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<IncidenciaEnfermedadResponseDTO> obtenerPorEnfermedad(UUID enfermedadId) {

        List<IncidenciaEnfermedad> lista = repository.findByEnfermedadId(enfermedadId);

        if (lista.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No hay incidencias registradas para la enfermedad con id: " + enfermedadId);
        }

        return lista.stream()
                .map(this::mapToDTO)
                .toList();
    }
}
