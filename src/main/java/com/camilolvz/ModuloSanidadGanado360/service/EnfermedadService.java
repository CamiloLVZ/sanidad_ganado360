package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import com.camilolvz.ModuloSanidadGanado360.repository.EnfermedadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnfermedadService {

    private final EnfermedadRepository repository;

    public EnfermedadService(EnfermedadRepository repository) {
        this.repository = repository;
    }

    public List<Enfermedad> listarTodas() {
        return repository.findAll();
    }

    public Enfermedad crear(String nombre) {
        Enfermedad e = new Enfermedad();
        e.setNombre(nombre);
        return repository.save(e);
    }

    public Enfermedad buscarPorNombre(String nombre) {
        return repository.findByNombreIgnoreCase(nombre);
    }


    public Enfermedad obtenerOcrear(String nombre) {
        Enfermedad existente = repository.findByNombreIgnoreCase(nombre);
        return existente != null ? existente : crear(nombre);
    }
}
