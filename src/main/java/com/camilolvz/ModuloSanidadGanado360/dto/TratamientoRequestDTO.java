package com.camilolvz.ModuloSanidadGanado360.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class TratamientoRequestDTO {

    @NotBlank
    private String idIndividuo;

    @NotBlank
    private String idFinca;

    @NotBlank
    private String tipoTratamiento;

    @NotBlank
    private String productoUsado;

    @NotBlank
    private String dosis;

    private String viaAdministracion;
    private String frecuenciaAplicacion;
    private Integer numeroAplicaciones;
    private String enfermedadObjetivo;

    @NotNull
    private Date fechaInicio;

    @NotNull
    private Date fechaFin;

    private Date fechaProximaDosis;

    @NotBlank
    private String nombreResponsable;

    @NotBlank
    private String estado;

    private String observaciones;

}
