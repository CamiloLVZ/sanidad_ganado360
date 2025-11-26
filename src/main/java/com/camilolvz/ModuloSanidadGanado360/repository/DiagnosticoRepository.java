package com.camilolvz.ModuloSanidadGanado360.repository;

import com.camilolvz.ModuloSanidadGanado360.model.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, UUID> {

    List<Diagnostico> findByIdIndividuo(UUID idIndividuo);
}
