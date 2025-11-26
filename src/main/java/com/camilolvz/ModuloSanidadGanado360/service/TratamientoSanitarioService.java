package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import com.camilolvz.ModuloSanidadGanado360.model.TratamientoSanitario;
import com.camilolvz.ModuloSanidadGanado360.repository.EnfermedadRepository;
import com.camilolvz.ModuloSanidadGanado360.repository.TratamientoSanitarioRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TratamientoSanitarioService {

    private final TratamientoSanitarioRepository tratamientoRepo;
    private final EnfermedadRepository enfermedadRepo;

    public TratamientoSanitarioService(
            TratamientoSanitarioRepository tratamientoRepo,
            EnfermedadRepository enfermedadRepo
    ) {
        this.tratamientoRepo = tratamientoRepo;
        this.enfermedadRepo = enfermedadRepo;
    }

    private Enfermedad obtenerOcrearEnfermedad(String nombre) {
        if (nombre == null || nombre.isBlank()) return null;

        return enfermedadRepo.findByNombreIgnoreCase(nombre)
                .orElseGet(() -> enfermedadRepo.save(new Enfermedad(nombre)));
    }

    private void validarFechas(TratamientoRequestDTO dto) {
        if (dto.getFechaFin().before(dto.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
    }

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
        t.setFechaInicio(dto.getFechaInicio());
        t.setFechaFin(dto.getFechaFin());
        t.setFechaProximaDosis(dto.getFechaProximaDosis());
        t.setNombreResponsable(dto.getNombreResponsable());
        t.setEstado(dto.getEstado());
        t.setObservaciones(dto.getObservaciones());


        Enfermedad enfermedad = obtenerOcrearEnfermedad(dto.getEnfermedadObjetivo());
        t.setEnfermedadObjetivo(enfermedad);

        return t;
    }

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
        dto.setFechaInicio(t.getFechaInicio());
        dto.setFechaFin(t.getFechaFin());
        dto.setFechaProximaDosis(t.getFechaProximaDosis());
        dto.setNombreResponsable(t.getNombreResponsable());
        dto.setEstado(t.getEstado());
        dto.setObservaciones(t.getObservaciones());

        if (t.getEnfermedadObjetivo() != null) {
            dto.setIdEnfermedad(t.getEnfermedadObjetivo().getId());
            dto.setNombreEnfermedad(t.getEnfermedadObjetivo().getNombre());
        }

        return dto;
    }

    public TratamientoResponseDTO crear(TratamientoRequestDTO dto) {
        validarFechas(dto);
        TratamientoSanitario entity = convertirAEntidad(dto);

        if (dto.getDiasParaRefuerzo() != null && dto.getDiasParaRefuerzo() > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dto.getFechaInicio());
            calendar.add(Calendar.DAY_OF_YEAR, dto.getDiasParaRefuerzo());
            entity.setFechaProximaDosis(calendar.getTime());
        }

        return convertirADTO(tratamientoRepo.save(entity));

    }

    public TratamientoResponseDTO actualizar(UUID id, TratamientoRequestDTO dto) {
        validarFechas(dto);

        TratamientoSanitario existente = tratamientoRepo.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }

        existente.setIdIndividuo(dto.getIdIndividuo());
        existente.setIdFinca(dto.getIdFinca());
        existente.setTipoTratamiento(dto.getTipoTratamiento());
        existente.setProductoUsado(dto.getProductoUsado());
        existente.setDosis(dto.getDosis());
        existente.setViaAdministracion(dto.getViaAdministracion());
        existente.setFrecuenciaAplicacion(dto.getFrecuenciaAplicacion());
        existente.setNumeroAplicaciones(dto.getNumeroAplicaciones());
        existente.setFechaInicio(dto.getFechaInicio());
        existente.setFechaFin(dto.getFechaFin());
        existente.setFechaProximaDosis(dto.getFechaProximaDosis());
        existente.setNombreResponsable(dto.getNombreResponsable());
        existente.setEstado(dto.getEstado());
        existente.setObservaciones(dto.getObservaciones());

        Enfermedad enfermedad = obtenerOcrearEnfermedad(dto.getEnfermedadObjetivo());
        existente.setEnfermedadObjetivo(enfermedad);

        return convertirADTO(tratamientoRepo.save(existente));
    }

    public TratamientoResponseDTO obtener(UUID id) {
        TratamientoSanitario t = tratamientoRepo.findById(id).orElse(null);
        return t != null ? convertirADTO(t) : null;
    }

    public List<TratamientoResponseDTO> obtenerPorIndividuo(UUID idIndividuo) {
        return tratamientoRepo.findByIdIndividuo(idIndividuo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<TratamientoResponseDTO> obtenerPorFinca(UUID idFinca) {
        return tratamientoRepo.findByIdFinca(idFinca)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public void eliminar(UUID id) {
        tratamientoRepo.deleteById(id);
    }
}
