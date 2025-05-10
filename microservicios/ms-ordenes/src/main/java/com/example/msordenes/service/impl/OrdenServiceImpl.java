package com.example.msordenes.service.impl;

import com.example.msordenes.client.AuthClient;
import com.example.msordenes.client.ProductoClient;
import com.example.msordenes.client.ProductoResponse;
import com.example.msordenes.dto.DetalleOrdenRequest;
import com.example.msordenes.dto.OrdenRequest;
import com.example.msordenes.dto.OrdenResponse;
import com.example.msordenes.exception.ResourceNotFoundException;
import com.example.msordenes.model.DetalleOrden;
import com.example.msordenes.model.Orden;
import com.example.msordenes.repository.OrdenRepository;
import com.example.msordenes.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final AuthClient authClient;
    private final ProductoClient productoClient;

    @Override
    @Transactional
    public OrdenResponse crearOrden(OrdenRequest request, String token) {
        // Validar token
        try {
            authClient.validateToken(token);  // Puede lanzar excepción si el token es inválido
        } catch (Exception e) {
            throw new IllegalStateException("Token no válido o expirado");
        }

        Orden orden = new Orden();
        orden.setUsuarioId(request.getUsuarioId());
        orden.setFechaOrden(LocalDateTime.now());
        orden.setEstado(Orden.EstadoOrden.PENDIENTE);

        BigDecimal total = BigDecimal.ZERO;
        for (DetalleOrdenRequest detalleRequest : request.getDetalles()) {
            ResponseEntity<ProductoResponse> response = productoClient.obtenerProducto(detalleRequest.getProductoId());
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new IllegalStateException("Producto no válido con ID: " + detalleRequest.getProductoId());
            }
            ProductoResponse producto = response.getBody();
            if (producto.getStock() < detalleRequest.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            DetalleOrden detalle = new DetalleOrden();
            detalle.setOrden(orden);
            detalle.setProductoId(producto.getId());
            detalle.setNombreProducto(producto.getNombre());  // Asumir que ProductoResponse tiene un nombre
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setPrecioUnitario(BigDecimal.valueOf(producto.getPrecio()));
            detalle.calcularSubtotal();

            orden.getDetalles().add(detalle);
            total = total.add(detalle.getSubtotal());

            // Actualizar stock del producto
            productoClient.actualizarStock(producto.getId(), -detalleRequest.getCantidad());
        }

        orden.setTotal(total);
        return OrdenResponse.fromEntity(ordenRepository.save(orden));
    }

    @Override
    public List<OrdenResponse> listarOrdenes() {
        return ordenRepository.findAll().stream()
                .map(OrdenResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdenResponse> listarOrdenesPorUsuario(String usuarioId) {
        return ordenRepository.findByUsuarioId(usuarioId).stream()
                .map(OrdenResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public OrdenResponse obtenerOrden(Long id) {
        return ordenRepository.findById(id)
                .map(OrdenResponse::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public OrdenResponse actualizarEstado(Long id, Orden.EstadoOrden estado) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con id: " + id));

        orden.setEstado(estado);
        return OrdenResponse.fromEntity(ordenRepository.save(orden));
    }

    @Override
    @Transactional
    public void eliminarOrden(Long id) {
        if (!ordenRepository.existsById(id)) {
            throw new ResourceNotFoundException("Orden no encontrada con id: " + id);
        }
        ordenRepository.deleteById(id);
    }
}
