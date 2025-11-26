package com.camilolvz.ModuloSanidadGanado360.repository;

import com.camilolvz.ModuloSanidadGanado360.model.TratamientoSanitario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TratamientoSanitarioRepository extends JpaRepository<TratamientoSanitario, UUID> {

    List<TratamientoSanitario> findByIdIndividuo(UUID idIndividuo);

    List<TratamientoSanitario> findByIdFinca(UUID idFinca);

    List<TratamientoSanitario> findByIdIndividuoAndFechaProximaDosisAfterAndEstado(
            UUID idIndividuo, Date fecha, String estado);

    List<TratamientoSanitario> findByIdFincaAndFechaProximaDosisAfterAndEstado(
            UUID idFinca, Date fecha, String estado);

    List<TratamientoSanitario> findByFechaProximaDosisAfterAndEstado(Date fecha, String estado);


}
