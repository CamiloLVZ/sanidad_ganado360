package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.mapper.IncidenciaTratamientoMapper;
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
    private final IncidenciaTratamientoMapper mapper;

    public IncidenciaTratamientoService(
            IncidenciaTratamientoRepository repository,
            TratamientoSanitarioRepository tratamientoRepository,
            IncidenciaTratamientoMapper mapper
    ) {
        this.repository = repository;
        this.tratamientoRepository = tratamientoRepository;
        this.mapper = mapper;
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

        IncidenciaTratamiento base = mapper.toEntity(req);
        IncidenciaTratamiento savedBase = repository.save(base);

        try {
            generarIncidenciasProgramadas(savedBase);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generando incidencias programadas: " + ex.getMessage());
        }

        return mapper.toDTO(savedBase);
    }

    // Listar todos
    public List<IncidenciaTratamientoResponseDTO> listarTodos() {
        return mapper.toDTOList(repository.findAll());
    }

    public IncidenciaTratamientoResponseDTO obtener(UUID id) {
        return repository.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));
    }

    // Obtener por animal
    public List<IncidenciaTratamientoResponseDTO> obtenerPorAnimal(UUID idAnimal) {
        return repository.findByIdAnimal(idAnimal).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    // Obtener por tratamiento
    public List<IncidenciaTratamientoResponseDTO> obtenerPorTratamiento(UUID tratamientoId) {
        return repository.findByTratamiento_Id(tratamientoId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    // Obtener por estado
    public List<IncidenciaTratamientoResponseDTO> obtenerPorEstado(EstadoIncidencia estado) {
        return repository.findByEstado(estado).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    // Actualizar (parcial)
    public IncidenciaTratamientoResponseDTO actualizar(UUID id, IncidenciaTratamientoRequestDTO req) {

        IncidenciaTratamiento existente = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));

        // Delegar actualización al mapper
        mapper.updateEntityFromRequest(req, existente, tratamientoRepository);

        IncidenciaTratamiento guardada = repository.save(existente);
        return mapper.toDTO(guardada);
    }

    // "Eliminar" lógico: marcar ANULADO
    public IncidenciaTratamientoResponseDTO anular(UUID id) {

        IncidenciaTratamiento existente = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));

        existente.setEstado(EstadoIncidencia.ANULADO);

        IncidenciaTratamiento guardada = repository.save(existente);
        return mapper.toDTO(guardada);
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
