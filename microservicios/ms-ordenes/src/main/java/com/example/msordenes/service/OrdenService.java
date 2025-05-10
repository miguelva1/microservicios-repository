package com.example.msordenes.service;

import com.example.msordenes.dto.OrdenRequest;
import com.example.msordenes.dto.OrdenResponse;
import com.example.msordenes.model.Orden;

import java.util.List;

public interface OrdenService {
    OrdenResponse crearOrden(OrdenRequest request, String token);
    List<OrdenResponse> listarOrdenes();
    List<OrdenResponse> listarOrdenesPorUsuario(String usuarioId);
    OrdenResponse obtenerOrden(Long id);
    OrdenResponse actualizarEstado(Long id, Orden.EstadoOrden estado);
    void eliminarOrden(Long id);
} 