package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.dto.DiagnosticoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.DiagnosticoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.service.DiagnosticoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    public DiagnosticoController(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    @PostMapping
    public ResponseEntity<DiagnosticoResponseDTO> crear(@RequestBody DiagnosticoRequestDTO dto) {
        return ResponseEntity.ok(diagnosticoService.diagnosticar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticoResponseDTO> obtenerPorId(@PathVariable UUID id) {
        DiagnosticoResponseDTO dto = diagnosticoService.obtener(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<DiagnosticoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(diagnosticoService.obtenerTodos());
    }

    @GetMapping("/individuo/{idIndividuo}")
    public ResponseEntity<List<DiagnosticoResponseDTO>> obtenerPorIndividuo(@PathVariable UUID idIndividuo) {
        return ResponseEntity.ok(diagnosticoService.obtenerPorIndividuo(idIndividuo));
    }

    @GetMapping("/finca/{idFinca}")
    public ResponseEntity<List<DiagnosticoResponseDTO>> obtenerPorFinca(@PathVariable UUID idFinca) {
        return ResponseEntity.ok(diagnosticoService.obtenerPorFinca(idFinca));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticoResponseDTO> actualizar(@PathVariable UUID id,
                                                             @RequestBody DiagnosticoRequestDTO dto) {
        DiagnosticoResponseDTO actualizado = diagnosticoService.actualizar(id, dto);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        diagnosticoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
