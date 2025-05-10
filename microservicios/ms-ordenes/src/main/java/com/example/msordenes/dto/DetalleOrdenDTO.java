package com.example.msordenes.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO para transferir datos de detalles de orden
// Se usa para crear y actualizar detalles de orden
@Data
public class DetalleOrdenDTO {
    // ID del producto
    @NotNull(message = "El ID del producto es requerido")
    private Long productoId;

    // Cantidad del producto
    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser mayor que cero")
    private Integer cantidad;
}