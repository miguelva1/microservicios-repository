package com.example.msordenes.controller;

import com.example.msordenes.dto.OrdenRequest;
import com.example.msordenes.dto.OrdenResponse;
import com.example.msordenes.model.Orden;
import com.example.msordenes.service.OrdenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<OrdenResponse> crearOrden(
            @Valid @RequestBody OrdenRequest request,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ordenService.crearOrden(request, token));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<OrdenResponse>> listarOrdenes() {
        return ResponseEntity.ok(ordenService.listarOrdenes());
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<OrdenResponse>> listarOrdenesPorUsuario(@PathVariable String usuarioId) {
        return ResponseEntity.ok(ordenService.listarOrdenesPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<OrdenResponse> obtenerOrden(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.obtenerOrden(id));
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<OrdenResponse> actualizarEstado(
            @PathVariable Long id,
            @RequestParam Orden.EstadoOrden estado) {
        return ResponseEntity.ok(ordenService.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        ordenService.eliminarOrden(id);
        return ResponseEntity.noContent().build();
    }
}