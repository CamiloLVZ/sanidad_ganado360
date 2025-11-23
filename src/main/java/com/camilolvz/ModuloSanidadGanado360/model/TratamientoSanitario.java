package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TratamientoSanitario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idIndividuo;
    private String idFinca;

    private String tipoTratamiento;
    private String productoUsado;
    private String dosis;
    private String viaAdministracion;
    private String frecuenciaAplicacion;
    private Integer numeroAplicaciones;
    private String enfermedadObjetivo;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Temporal(TemporalType.DATE)
    private Date fechaProximaDosis;

    private String nombreResponsable;

    private String estado;

    @Column(length = 2000)
    private String observaciones;

}
