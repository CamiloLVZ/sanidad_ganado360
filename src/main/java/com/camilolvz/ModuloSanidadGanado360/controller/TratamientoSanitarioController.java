package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.service.TratamientoSanitarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @PathVariable Long id,
            @Valid @RequestBody TratamientoRequestDTO dto) {
        return service.actualizar(id, dto);
    }

    @GetMapping("/{id}")
    public TratamientoResponseDTO obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @GetMapping("/individuo/{idIndividuo}")
    public List<TratamientoResponseDTO> obtenerPorIndividuo(@PathVariable String idIndividuo) {
        return service.obtenerPorIndividuo(idIndividuo);
    }

    @GetMapping("/finca/{idFinca}")
    public List<TratamientoResponseDTO> obtenerPorFinca(@PathVariable String idFinca) {
        return service.obtenerPorFinca(idFinca);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
