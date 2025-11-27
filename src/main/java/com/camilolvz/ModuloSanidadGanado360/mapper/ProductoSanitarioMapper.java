package com.camilolvz.ModuloSanidadGanado360.mapper;


import com.camilolvz.ModuloSanidadGanado360.dto.ProductoSanitarioRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.ProductoSanitarioResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.ProductoSanitario;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mapper responsable de convertir entre entidad <-> DTO (request/response).
 * Mantén la lógica de mapeo aquí para que los DTOs se queden limpios.
 */
@Component
public class ProductoSanitarioMapper {

    /**
     * Construye una entidad nueva a partir del request DTO.
     * No pone el id (JPA lo generará).
     */
    public ProductoSanitario toEntity(ProductoSanitarioRequestDTO req) {
        if (req == null) return null;
        ProductoSanitario p = new ProductoSanitario();
        p.setNombre(req.getNombre());
        p.setTipo(req.getTipo());
        p.setEspecie(req.getEspecie());
        return p;
    }

    /**
     * Actualiza una entidad existente (parcial) a partir del request DTO.
     * - No toca el id si es null.
     */
    public void updateEntityFromRequest(ProductoSanitario existing, ProductoSanitarioRequestDTO req) {
        if (existing == null || req == null) return;
        if (req.getNombre() != null && !req.getNombre().isBlank()) existing.setNombre(req.getNombre());
        if (req.getTipo() != null) existing.setTipo(req.getTipo());
        if (req.getEspecie() != null) existing.setEspecie(req.getEspecie());
    }

    /**
     * Convierte entidad a DTO de respuesta desacoplado.
     */
    public ProductoSanitarioResponseDTO toResponseDto(ProductoSanitario entity) {
        if (entity == null) return null;
        UUID id = entity.getId();
        return new ProductoSanitarioResponseDTO(
                id,
                entity.getNombre(),
                entity.getTipo(),
                entity.getEspecie()
        );
    }
}
