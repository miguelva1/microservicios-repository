package com.example.msordenes.dto;

import com.example.msordenes.model.DetalleOrden;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetalleOrdenResponse {
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public static DetalleOrdenResponse fromEntity(DetalleOrden detalle) {
        DetalleOrdenResponse response = new DetalleOrdenResponse();
        response.setId(detalle.getId());
        response.setProductoId(detalle.getProductoId());
        response.setCantidad(detalle.getCantidad());
        response.setPrecioUnitario(detalle.getPrecioUnitario());
        response.setSubtotal(detalle.getSubtotal());
        return response;
    }
} 