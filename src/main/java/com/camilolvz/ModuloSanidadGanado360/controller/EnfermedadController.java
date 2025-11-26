package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import com.camilolvz.ModuloSanidadGanado360.service.EnfermedadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enfermedades")
public class EnfermedadController {

    private final EnfermedadService service;
    public EnfermedadController(EnfermedadService service) {
        this.service = service;
    }
    @GetMapping("/all")
    public List<Enfermedad> listarEnfermedades() {
        return service.listarTodas();
    }
}
