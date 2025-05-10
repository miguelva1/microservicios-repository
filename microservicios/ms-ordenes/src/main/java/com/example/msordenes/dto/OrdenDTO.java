package com.example.msordenes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

// DTO para transferir datos de órdenes
// Se usa para crear y actualizar órdenes
@Data
public class OrdenDTO {
    // ID del usuario que realiza la orden
    @NotNull(message = "El ID del usuario es requerido")
    private Long usuarioId;

    // Dirección de entrega
    @NotNull(message = "La dirección de entrega es requerida")
    private String direccionEntrega;

    // Lista de detalles de la orden
    @NotEmpty(message = "La orden debe tener al menos un detalle")
    @Valid
    private List<DetalleOrdenDTO> detalles;
} 