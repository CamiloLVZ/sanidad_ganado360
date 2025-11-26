package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.DiagnosticoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.DiagnosticoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.Diagnostico;
import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import com.camilolvz.ModuloSanidadGanado360.repository.DiagnosticoRepository;
import com.camilolvz.ModuloSanidadGanado360.repository.EnfermedadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiagnosticoService {

    private final DiagnosticoRepository diagnosticoRepository;
    private final EnfermedadRepository enfermedadRepository;

    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository, EnfermedadRepository enfermedadRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.enfermedadRepository = enfermedadRepository;
    }

    public DiagnosticoResponseDTO diagnosticar(DiagnosticoRequestDTO dto) {

        Enfermedad enfermedad = enfermedadRepository
                .findByNombreIgnoreCase(dto.getNombreEnfermedad())
                .orElseGet(() -> {
                    Enfermedad nueva = new Enfermedad();
                    nueva.setNombre(dto.getNombreEnfermedad());
                    return enfermedadRepository.save(nueva);
                });

        Diagnostico d = new Diagnostico();
        d.setIdIndividuo(dto.getIdIndividuo());
        d.setIdFinca(dto.getIdFinca());
        d.setFechaDiagnostico(dto.getFechaDiagnostico());
        d.setResponsable(dto.getResponsable());
        d.setObservaciones(dto.getObservaciones());
        d.setEnfermedad(enfermedad);

        return convertirADTO(diagnosticoRepository.save(d));
    }

    public List<DiagnosticoResponseDTO> obtenerPorIndividuo(UUID idIndividuo) {
        return diagnosticoRepository.findByIdIndividuo(idIndividuo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<String> listarEnfermedades() {
        return enfermedadRepository.findAll()
                .stream()
                .map(Enfermedad::getNombre)
                .collect(Collectors.toList());
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
