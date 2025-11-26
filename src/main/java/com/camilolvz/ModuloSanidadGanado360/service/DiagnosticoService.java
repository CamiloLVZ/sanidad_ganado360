package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.DiagnosticoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.DiagnosticoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.Diagnostico;
import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import com.camilolvz.ModuloSanidadGanado360.repository.DiagnosticoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiagnosticoService {

    private final DiagnosticoRepository diagnosticoRepository;
    private final EnfermedadService enfermedadService;

    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository,
                              EnfermedadService enfermedadService) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.enfermedadService = enfermedadService;
    }

    public DiagnosticoResponseDTO diagnosticar(DiagnosticoRequestDTO dto) {

        Enfermedad enfermedad = enfermedadService.obtenerOcrear(dto.getNombreEnfermedad());

        Diagnostico d = new Diagnostico();
        d.setIdIndividuo(dto.getIdIndividuo());
        d.setIdFinca(dto.getIdFinca());
        d.setFechaDiagnostico(dto.getFechaDiagnostico());
        d.setResponsable(dto.getResponsable());
        d.setObservaciones(dto.getObservaciones());
        d.setEnfermedad(enfermedad);

        return convertirADTO(diagnosticoRepository.save(d));
    }

    public DiagnosticoResponseDTO obtener(UUID id) {
        Diagnostico d = diagnosticoRepository.findById(id).orElse(null);
        return d != null ? convertirADTO(d) : null;
    }

    public List<DiagnosticoResponseDTO> obtenerTodos() {
        return diagnosticoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<DiagnosticoResponseDTO> obtenerPorIndividuo(UUID idIndividuo) {
        return diagnosticoRepository.findByIdIndividuo(idIndividuo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }


    public List<DiagnosticoResponseDTO> obtenerPorFinca(UUID idFinca) {
        return diagnosticoRepository.findByIdFinca(idFinca)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public DiagnosticoResponseDTO actualizar(UUID id, DiagnosticoRequestDTO dto) {

        Diagnostico existente = diagnosticoRepository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }

        Enfermedad enfermedad = enfermedadService.obtenerOcrear(dto.getNombreEnfermedad());

        existente.setIdIndividuo(dto.getIdIndividuo());
        existente.setIdFinca(dto.getIdFinca());
        existente.setFechaDiagnostico(dto.getFechaDiagnostico());
        existente.setResponsable(dto.getResponsable());
        existente.setObservaciones(dto.getObservaciones());
        existente.setEnfermedad(enfermedad);

        return convertirADTO(diagnosticoRepository.save(existente));
    }


    public void eliminar(UUID id) {
        diagnosticoRepository.deleteById(id);
    }

    private DiagnosticoResponseDTO convertirADTO(Diagnostico d) {
        DiagnosticoResponseDTO dto = new DiagnosticoResponseDTO();
        dto.setId(d.getId());
        dto.setIdIndividuo(d.getIdIndividuo());
        dto.setIdFinca(d.getIdFinca());
        dto.setNombreEnfermedad(d.getEnfermedad().getNombre());
        dto.setFechaDiagnostico(d.getFechaDiagnostico());
        dto.setResponsable(d.getResponsable());
        dto.setObservaciones(d.getObservaciones());
        return dto;
    }
}
