package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.dto.DiagnosticoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.DiagnosticoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.service.DiagnosticoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoController {

    private final DiagnosticoService service;

    public DiagnosticoController(DiagnosticoService service) {
        this.service = service;
    }

    @PostMapping
    public DiagnosticoResponseDTO diagnosticar(@RequestBody DiagnosticoRequestDTO dto) {
        return service.diagnosticar(dto);
    }

    @GetMapping("/individuo/{idIndividuo}")
    public List<DiagnosticoResponseDTO> obtenerPorIndividuo(@PathVariable UUID idIndividuo) {
        return service.obtenerPorIndividuo(idIndividuo);
    }

    @GetMapping("/enfermedades")
    public List<String> listarEnfermedades() {
        return service.listarEnfermedades();
    }
}
