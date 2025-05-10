package com.example.msordenes.dto;

import com.example.msordenes.model.Orden;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrdenResponse {
    private Long id;
    private String usuarioId;
    private LocalDateTime fechaOrden;
    private Orden.EstadoOrden estado;
    private BigDecimal total;
    private List<DetalleOrdenResponse> detalles;

    public static OrdenResponse fromEntity(Orden orden) {
        OrdenResponse response = new OrdenResponse();
        response.setId(orden.getId());
        response.setUsuarioId(orden.getUsuarioId());
        response.setFechaOrden(orden.getFechaOrden());
        response.setEstado(orden.getEstado());
        response.setTotal(orden.getTotal());
        response.setDetalles(orden.getDetalles().stream()
                .map(DetalleOrdenResponse::fromEntity)
                .collect(Collectors.toList()));
        return response;
    }
} 