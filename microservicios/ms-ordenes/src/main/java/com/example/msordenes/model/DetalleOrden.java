package com.example.msordenes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// Entidad que representa un detalle de una orden
// Almacena la información de cada producto en la orden
@Entity
@Table(name = "detalles_orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleOrden {
    // ID único del detalle
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Orden a la que pertenece este detalle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden orden;

    // ID del producto
    @NotNull(message = "El ID del producto es requerido")
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    // Nombre del producto
    @NotNull(message = "El nombre del producto es requerido")
    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    // Cantidad del producto
    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor que cero")
    @Column(nullable = false)
    private Integer cantidad;

    // Precio unitario del producto
    @NotNull(message = "El precio unitario es requerido")
    @Positive(message = "El precio unitario debe ser mayor que cero")
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // Subtotal del detalle (cantidad * precio unitario)
    @NotNull(message = "El subtotal es requerido")
    @Positive(message = "El subtotal debe ser mayor que cero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // Método para calcular el subtotal del detalle
    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }
} 