package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.service.TratamientoSanitarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tratamientos")
@CrossOrigin("*")
public class TratamientoSanitarioController {

    private final TratamientoSanitarioService service;

    public TratamientoSanitarioController(TratamientoSanitarioService service) {
        this.service = service;
    }

    @PostMapping
    public TratamientoResponseDTO crear(@Valid @RequestBody TratamientoRequestDTO dto) {
        return service.crear(dto);
    }

    @PutMapping("/{id}")
    public TratamientoResponseDTO actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody TratamientoRequestDTO dto) {
        return service.actualizar(id, dto);
    }

    @GetMapping("/{id}")
    public TratamientoResponseDTO obtener(@PathVariable UUID id) {
        return service.obtener(id);
    }

    @GetMapping("/individuo/{idIndividuo}")
    public List<TratamientoResponseDTO> obtenerPorIndividuo(@PathVariable UUID idIndividuo) {
        return service.obtenerPorIndividuo(idIndividuo);
    }

    @GetMapping("/finca/{idFinca}")
    public List<TratamientoResponseDTO> obtenerPorFinca(@PathVariable UUID idFinca) {
        return service.obtenerPorFinca(idFinca);
    }

    @PostMapping("/vacunas")
    public ResponseEntity<TratamientoResponseDTO> registrarVacuna(
            @Valid @RequestBody TratamientoRequestDTO dto) {

        dto.setTipoTratamiento("Vacuna");

        TratamientoResponseDTO creado = service.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/recordatorios")
    public ResponseEntity<List<TratamientoResponseDTO>> obtenerRecordatorios() {
        return ResponseEntity.ok(service.obtenerRecordatorios());
    }

    @GetMapping("/recordatorios/individuo/{idIndividuo}")
    public ResponseEntity<List<TratamientoResponseDTO>> obtenerRecordatoriosPorIndividuo(
            @PathVariable UUID idIndividuo) {

        return ResponseEntity.ok(service.obtenerRecordatoriosPorIndividuo(idIndividuo));
    }

    @GetMapping("/recordatorios/finca/{idFinca}")
    public ResponseEntity<List<TratamientoResponseDTO>> obtenerRecordatoriosPorFinca(
            @PathVariable UUID idFinca) {

        return ResponseEntity.ok(service.obtenerRecordatoriosPorFinca(idFinca));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable UUID id) {
        service.eliminar(id);
    }
}
