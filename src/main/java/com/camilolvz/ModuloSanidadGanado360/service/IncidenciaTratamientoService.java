package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.EstadoIncidencia;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaTratamiento;
import com.camilolvz.ModuloSanidadGanado360.model.TiempoUnidad;
import com.camilolvz.ModuloSanidadGanado360.model.TratamientoSanitario;
import com.camilolvz.ModuloSanidadGanado360.repository.IncidenciaTratamientoRepository;
import com.camilolvz.ModuloSanidadGanado360.repository.TratamientoSanitarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IncidenciaTratamientoService {

    private final IncidenciaTratamientoRepository repository;
    private final TratamientoSanitarioRepository tratamientoRepository;

    public IncidenciaTratamientoService(
            IncidenciaTratamientoRepository repository,
            TratamientoSanitarioRepository tratamientoRepository
    ) {
        this.repository = repository;
        this.tratamientoRepository = tratamientoRepository;
    }

    /**
     * Mapea entidad a DTO (implementado aquí porque el DTO no tenía fromEntity estático).
     * Ajusta el getter de nombre del tratamiento si tu entidad usa otro getter distinto a getNombre().
     */
    private IncidenciaTratamientoResponseDTO mapToDTO(IncidenciaTratamiento e) {
        IncidenciaTratamientoResponseDTO dto = new IncidenciaTratamientoResponseDTO();
        dto.setId(e.getId());
        dto.setIdAnimal(e.getIdAnimal());

        if (e.getTratamiento() != null) {
            dto.setTratamientoId(e.getTratamiento().getId());
            // Asumo que TratamientoSanitario tiene getNombre(); cámbialo si es distinto.
            try {
                dto.setTratamientoNombre(e.getTratamiento().getNombre());
            } catch (Exception ex) {
                // Si no existe getNombre, dejará null (evitar NPE)
                dto.setTratamientoNombre(null);
            }
        } else {
            dto.setTratamientoId(null);
            dto.setTratamientoNombre(null);
        }

        dto.setResponsable(e.getResponsable());
        dto.setFechaTratamiento(e.getFechaTratamiento());
        dto.setEstado(e.getEstado());
        return dto;
    }

    private IncidenciaTratamiento convertirAEntidad(IncidenciaTratamientoRequestDTO req) {
        IncidenciaTratamiento i = new IncidenciaTratamiento();

        // Obtener tratamiento por id (debe existir)
        TratamientoSanitario tratamiento = tratamientoRepository.findById(req.getTratamientoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tratamiento no encontrado con id: " + req.getTratamientoId()));
        i.setTratamiento(tratamiento);

        i.setIdAnimal(req.getIdAnimal());
        i.setResponsable(req.getResponsable());
        i.setFechaTratamiento(req.getFechaTratamiento());

        if (req.getEstado() != null && !req.getEstado().isBlank()) {
            try {
                i.setEstado(EstadoIncidencia.valueOf(req.getEstado().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido. Valores permitidos: PENDIENTE, REALIZADO, ANULADO");
            }
        } else {
            i.setEstado(EstadoIncidencia.PENDIENTE);
        }

        return i;
    }

    /**
     * Crea la incidencia base y genera (si aplica) las incidencias programadas
     * usando duracionTotal + intervalo del Tratamiento.
     */
    @Transactional
    public IncidenciaTratamientoResponseDTO crear(IncidenciaTratamientoRequestDTO req) {
        if (req.getFechaTratamiento() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fechaTratamiento es obligatoria");
        }

        // Persistir incidencia base
        IncidenciaTratamiento base = convertirAEntidad(req);
        IncidenciaTratamiento savedBase = repository.save(base);

        // Generar incidencias programadas (si el tratamiento define duracionTotal e intervalo)
        try {
            generarIncidenciasProgramadas(savedBase);
        } catch (Exception ex) {
            // Hacemos rollback para no dejar datos parciales
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generando incidencias programadas: " + ex.getMessage());
        }

        return mapToDTO(savedBase);
    }

    // Listar todos
    public List<IncidenciaTratamientoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Obtener por id
    public IncidenciaTratamientoResponseDTO obtener(UUID id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));
    }

    // Obtener por animal
    public List<IncidenciaTratamientoResponseDTO> obtenerPorAnimal(UUID idAnimal) {
        return repository.findByIdAnimal(idAnimal).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Obtener por tratamiento
    public List<IncidenciaTratamientoResponseDTO> obtenerPorTratamiento(UUID tratamientoId) {
        return repository.findByTratamiento_Id(tratamientoId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Obtener por estado
    public List<IncidenciaTratamientoResponseDTO> obtenerPorEstado(EstadoIncidencia estado) {
        return repository.findByEstado(estado).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Actualizar (parcial)
    public IncidenciaTratamientoResponseDTO actualizar(UUID id, IncidenciaTratamientoRequestDTO req) {
        IncidenciaTratamiento existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));

        if (req.getTratamientoId() != null) {
            TratamientoSanitario t = tratamientoRepository.findById(req.getTratamientoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tratamiento no encontrado con id: " + req.getTratamientoId()));
            existente.setTratamiento(t);
        }

        if (req.getIdAnimal() != null) existente.setIdAnimal(req.getIdAnimal());
        if (req.getResponsable() != null && !req.getResponsable().isBlank()) existente.setResponsable(req.getResponsable());
        if (req.getFechaTratamiento() != null) existente.setFechaTratamiento(req.getFechaTratamiento());

        if (req.getEstado() != null && !req.getEstado().isBlank()) {
            try {
                EstadoIncidencia nuevo = EstadoIncidencia.valueOf(req.getEstado().toUpperCase());
                existente.setEstado(nuevo);
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido. Valores permitidos: PENDIENTE, REALIZADO, ANULADO");
            }
        }

        IncidenciaTratamiento guardada = repository.save(existente);
        return mapToDTO(guardada);
    }

    // "Eliminar" lógico: marcar ANULADO
    public IncidenciaTratamientoResponseDTO anular(UUID id) {
        IncidenciaTratamiento existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));

        existente.setEstado(EstadoIncidencia.ANULADO);
        IncidenciaTratamiento guardada = repository.save(existente);
        return mapToDTO(guardada);
    }

    /**
     * Comprueba si hay incidencias activas para un animal y tratamiento.
     * Consideramos 'activo' a todo estado distinto de ANULADO.
     */
    public boolean hayIncidenciasActivas(UUID idAnimal, UUID tratamientoId) {
        // existe algún registro para (animal, tratamiento) cuyo estado NO sea ANULADO
        return repository.existsByIdAnimalAndTratamiento_IdAndEstadoNot(idAnimal, tratamientoId, EstadoIncidencia.ANULADO);
    }

    /**
     * Genera las incidencias programadas (PENDIENTE) según el tratamiento:
     * - Usa duracionTotalCantidad/unidad y intervaloCantidad/unidad del TratamientoSanitario
     * - Crea incidencias en fechas: start + intervalo, start + 2*intervalo, ... hasta <= start + duracionTotal
     * - Evita duplicados verificando si ya existe incidencia para misma fecha (mismo animal+tratamiento).
     */
    private void generarIncidenciasProgramadas(IncidenciaTratamiento base) {
        TratamientoSanitario t = base.getTratamiento();

        if (t == null) return;

        Integer durTotalCantidad = t.getDuracionTotalCantidad();
        TiempoUnidad durTotalUnidad = t.getDuracionTotalUnidad();

        Integer intervaloCantidad = t.getIntervaloCantidad();
        TiempoUnidad intervaloUnidad = t.getIntervaloUnidad();

        // Si no hay intervalo o duracion definida, no generamos
        if (durTotalCantidad == null || durTotalCantidad <= 0 || durTotalUnidad == null) return;
        if (intervaloCantidad == null || intervaloCantidad <= 0 || intervaloUnidad == null) return;

        // Fecha base (sin parte horaria)
        LocalDate fechaBase = toLocalDate(base.getFechaTratamiento());
        LocalDate fechaFin = fechaBase.plus(durTotalCantidad, durTotalUnidad.toChronoUnit());

        LocalDate siguiente = fechaBase.plus(intervaloCantidad, intervaloUnidad.toChronoUnit());

        List<IncidenciaTratamiento> creadas = new ArrayList<>();
        while (!siguiente.isAfter(fechaFin)) {
            Date fechaSiguienteDate = fromLocalDate(siguiente);

            // Evitar duplicados: si ya existe una incidencia para mismo animal+tratamiento+fecha, saltarla
            boolean existe = repository.existsByIdAnimalAndTratamiento_IdAndFechaTratamiento(
                    base.getIdAnimal(), t.getId(), fechaSiguienteDate
            );
            if (!existe) {
                IncidenciaTratamiento nueva = new IncidenciaTratamiento();
                nueva.setTratamiento(t);
                nueva.setIdAnimal(base.getIdAnimal());
                nueva.setResponsable(base.getResponsable());
                nueva.setFechaTratamiento(fechaSiguienteDate);
                nueva.setEstado(EstadoIncidencia.PENDIENTE);

                creadas.add(nueva);
            }

            siguiente = siguiente.plus(intervaloCantidad, intervaloUnidad.toChronoUnit());
        }

        if (!creadas.isEmpty()) {
            repository.saveAll(creadas);
        }
    }

    private LocalDate toLocalDate(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date fromLocalDate(LocalDate ld) {
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
