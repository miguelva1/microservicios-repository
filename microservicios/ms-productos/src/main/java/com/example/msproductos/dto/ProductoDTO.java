package com.example.msproductos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

// DTO para transferir datos de productos
// Se usa para crear y actualizar productos
@Data
public class ProductoDTO {
    // Nombre del producto (requerido, entre 3 y 100 caracteres)
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    // Descripción del producto (máximo 1000 caracteres)
    @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres")
    private String descripcion;

    // Precio del producto (requerido, mínimo 0)
    @NotNull(message = "El precio es requerido")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private BigDecimal precio;

    // Stock disponible (requerido, mínimo 0)
    @NotNull(message = "El stock es requerido")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private Integer stock;

    // Categoría del producto (requerida)
    @NotBlank(message = "La categoría es requerida")
    private String categoria;

    // URL de la imagen del producto
    private String imagenUrl;
} 