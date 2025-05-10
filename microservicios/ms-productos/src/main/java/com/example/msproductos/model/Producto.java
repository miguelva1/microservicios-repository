package com.example.msproductos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// Entidad que representa un producto en el sistema
// Almacena la información básica de cada producto
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    // ID único del producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del producto
    @Column(nullable = false)
    private String nombre;

    // Descripción detallada del producto
    @Column(length = 1000)
    private String descripcion;

    // Precio del producto
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    // Cantidad disponible en inventario
    @Column(nullable = false)
    private Integer stock;

    // Categoría del producto
    @Column(nullable = false)
    private String categoria;

    // URL de la imagen del producto
    private String imagenUrl;

    // Indica si el producto está activo
    @Column(nullable = false)
    private Boolean activo = true;
} 