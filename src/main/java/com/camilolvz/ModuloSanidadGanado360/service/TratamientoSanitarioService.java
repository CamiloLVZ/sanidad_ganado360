package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.TratamientoSanitario;
import com.camilolvz.ModuloSanidadGanado360.repository.TratamientoSanitarioRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TratamientoSanitarioService {

    private final TratamientoSanitarioRepository repository;
    private final ProductoSanitarioService productoService;
    private final EnfermedadService enfermedadService;

    public TratamientoSanitarioService(
            TratamientoSanitarioRepository repository,
            ProductoSanitarioService productoService,
            EnfermedadService enfermedadService
    ) {
        this.repository = repository;
        this.productoService = productoService;
        this.enfermedadService = enfermedadService;
    }

    private void validarFechas(TratamientoRequestDTO dto) {
        if (dto.getFechaFin().before(dto.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
    }

    public List<TratamientoResponseDTO> obtenerRecordatorios() {
        return repository
                .findByFechaProximaDosisAfterAndEstado(new Date(), "pendiente")
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public List<TratamientoResponseDTO> obtenerRecordatoriosPorIndividuo(UUID idIndividuo) {
        Date hoy = new Date();
        return repository.findByIdIndividuoAndFechaProximaDosisAfterAndEstado(
                        idIndividuo, hoy, "pendiente")
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<TratamientoResponseDTO> obtenerRecordatoriosPorFinca(UUID idFinca) {
        Date hoy = new Date();
        return repository.findByIdFincaAndFechaProximaDosisAfterAndEstado(
                        idFinca, hoy, "pendiente")
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // DTO → Entity
    private TratamientoSanitario convertirAEntidad(TratamientoRequestDTO dto) {

        TratamientoSanitario t = new TratamientoSanitario();

        t.setIdIndividuo(dto.getIdIndividuo());
        t.setIdFinca(dto.getIdFinca());
        t.setTipoTratamiento(dto.getTipoTratamiento());
        t.setDosis(dto.getDosis());
        t.setFrecuenciaAplicacion(dto.getFrecuenciaAplicacion());
        t.setNumeroAplicaciones(dto.getNumeroAplicaciones());

        // Registrar producto si no existe
        t.setProductoUsado(
                productoService.obtenerOcrear(dto.getProductoUsado())
        );

        if (dto.getEnfermedadObjetivo() != null && !dto.getEnfermedadObjetivo().isBlank()) {
            t.setEnfermedadObjetivo(
                    enfermedadService.obtenerOcrear(dto.getEnfermedadObjetivo())
            );
        } else {
            t.setEnfermedadObjetivo(null);
        }

        t.setFechaInicio(dto.getFechaInicio());
        t.setFechaFin(dto.getFechaFin());
        t.setFechaProximaDosis(dto.getFechaProximaDosis());
        t.setNombreResponsable(dto.getNombreResponsable());
        t.setEstado(dto.getEstado());
        t.setObservaciones(dto.getObservaciones());

        return t;
    }

    // Entity → DTO
    private TratamientoResponseDTO convertirADTO(TratamientoSanitario t) {
        TratamientoResponseDTO dto = new TratamientoResponseDTO();

        dto.setId(t.getId());
        dto.setIdIndividuo(t.getIdIndividuo());
        dto.setIdFinca(t.getIdFinca());
        dto.setTipoTratamiento(t.getTipoTratamiento());
        dto.setDosis(t.getDosis());
        dto.setFrecuenciaAplicacion(t.getFrecuenciaAplicacion());
        dto.setNumeroAplicaciones(t.getNumeroAplicaciones());

        dto.setProductoUsado(t.getProductoUsado().getNombre());
        dto.setEnfermedadObjetivo(t.getEnfermedadObjetivo() != null
                ? t.getEnfermedadObjetivo().getNombre()
                : null
        );

        dto.setFechaInicio(t.getFechaInicio());
        dto.setFechaFin(t.getFechaFin());
        dto.setFechaProximaDosis(t.getFechaProximaDosis());
        dto.setNombreResponsable(t.getNombreResponsable());
        dto.setEstado(t.getEstado());
        dto.setObservaciones(t.getObservaciones());

        return dto;
    }

    public TratamientoResponseDTO crear(TratamientoRequestDTO dto) {
        validarFechas(dto);
        TratamientoSanitario entity = convertirAEntidad(dto);
        return convertirADTO(repository.save(entity));
    }

    public TratamientoResponseDTO obtener(UUID id) {
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    public List<TratamientoResponseDTO> obtenerPorIndividuo(UUID idIndividuo) {
        return repository.findByIdIndividuo(idIndividuo)
                .stream().map(this::convertirADTO).toList();
    }

    public List<TratamientoResponseDTO> obtenerPorFinca(UUID idFinca) {
        return repository.findByIdFinca(idFinca)
                .stream().map(this::convertirADTO).toList();
    }

    public TratamientoResponseDTO actualizar(UUID id, TratamientoRequestDTO dto) {
        if (dto.getFechaInicio() != null && dto.getFechaFin() != null) {
            if (dto.getFechaFin().before(dto.getFechaInicio())) {
                throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
            }
        }

        TratamientoSanitario existente = repository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }

      if (dto.getIdIndividuo() != null) existente.setIdIndividuo(dto.getIdIndividuo());
        if (dto.getIdFinca() != null) existente.setIdFinca(dto.getIdFinca());
        if (dto.getTipoTratamiento() != null && !dto.getTipoTratamiento().isBlank())
            existente.setTipoTratamiento(dto.getTipoTratamiento());

        if (dto.getDosis() != null && !dto.getDosis().isBlank()) existente.setDosis(dto.getDosis());
        if (dto.getFrecuenciaAplicacion() != null) existente.setFrecuenciaAplicacion(dto.getFrecuenciaAplicacion());
        if (dto.getNumeroAplicaciones() != null) existente.setNumeroAplicaciones(dto.getNumeroAplicaciones());

        if (dto.getProductoUsado() != null && !dto.getProductoUsado().isBlank()) {
            existente.setProductoUsado(productoService.obtenerOcrear(dto.getProductoUsado()));
        }

        if (dto.getEnfermedadObjetivo() != null) {
            if (!dto.getEnfermedadObjetivo().isBlank()) {
                existente.setEnfermedadObjetivo(enfermedadService.obtenerOcrear(dto.getEnfermedadObjetivo()));
            } else {
                existente.setEnfermedadObjetivo(null);
            }
        }

        if (dto.getFechaInicio() != null) existente.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) existente.setFechaFin(dto.getFechaFin());
        if (dto.getFechaProximaDosis() != null) existente.setFechaProximaDosis(dto.getFechaProximaDosis());

        if (dto.getNombreResponsable() != null && !dto.getNombreResponsable().isBlank())
            existente.setNombreResponsable(dto.getNombreResponsable());

        if (dto.getEstado() != null && !dto.getEstado().isBlank()) existente.setEstado(dto.getEstado());
        if (dto.getObservaciones() != null) existente.setObservaciones(dto.getObservaciones());

        TratamientoSanitario guardado = repository.save(existente);
        return convertirADTO(guardado);
    }

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
