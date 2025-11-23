package com.camilolvz.ModuloSanidadGanado360.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TratamientoResponseDTO {

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
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaProximaDosis;
    private String nombreResponsable;
    private String estado;
    private String observaciones;

}
