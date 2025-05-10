package com.example.msordenes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Entidad que representa una orden de compra en el sistema
// Almacena la información de una orden y sus detalles
@Entity
@Table(name = "ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orden {
    // ID único de la orden
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del usuario que realizó la orden
    @NotBlank(message = "El ID del usuario es requerido")
    @Column(name = "usuario_id", nullable = false)
    private String usuarioId;

    // Fecha y hora de creación de la orden
    @NotNull(message = "La fecha de la orden es requerida")
    @Column(name = "fecha_orden", nullable = false)
    private LocalDateTime fechaOrden;

    // Estado actual de la orden (PENDIENTE, CONFIRMADA, ENVIADA, ENTREGADA, CANCELADA)
    @NotNull(message = "El estado de la orden es requerido")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOrden estado;

    // Dirección de entrega
    @Column(nullable = false)
    private String direccionEntrega;

    // Total de la orden
    @NotNull(message = "El total de la orden es requerido")
    @Positive(message = "El total debe ser mayor que cero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // Lista de detalles de la orden
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrden> detalles = new ArrayList<>();

    // Método para calcular el total de la orden
    public void calcularTotal() {
        this.total = detalles.stream()
                .map(DetalleOrden::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public enum EstadoOrden {
        PENDIENTE,
        CONFIRMADA,
        ENVIADA,
        ENTREGADA,
        CANCELADA
    }
} 