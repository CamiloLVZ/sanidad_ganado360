package com.camilolvz.ModuloSanidadGanado360.repository;

import com.camilolvz.ModuloSanidadGanado360.model.TratamientoSanitario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TratamientoSanitarioRepository extends JpaRepository<TratamientoSanitario, Long> {

    List<TratamientoSanitario> findByIdIndividuo(String idIndividuo);

    List<TratamientoSanitario> findByIdFinca(String idFinca);
}
