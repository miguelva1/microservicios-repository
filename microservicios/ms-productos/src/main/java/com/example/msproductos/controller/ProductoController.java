package com.example.msproductos.controller;


import com.example.msproductos.client.AuthClient;
import com.example.msproductos.dto.ProductoDTO;
import com.example.msproductos.model.Producto;
import com.example.msproductos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para gestionar productos
// Expone endpoints para operaciones CRUD y gestión de stock
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final AuthClient authClient;

    // Crea un nuevo producto
    // Requiere rol ADMIN
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.crearProducto(productoDTO));
    }

    // Obtiene todos los productos activos
    // Accesible para todos los usuarios autenticados
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Producto>> obtenerProductos() {
        return ResponseEntity.ok(productoService.obtenerProductos());
    }

    // Obtiene un producto por su ID
    // Accesible para todos los usuarios autenticados
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerProducto(id));
    }

    // Actualiza un producto existente
    // Requiere rol ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, productoDTO));
    }

    // Elimina un producto (marcándolo como inactivo)
    // Requiere rol ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok().build();
    }

    // Actualiza el stock de un producto
    // Requiere rol ADMIN
    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Producto> actualizarStock(
            @PathVariable Long id,
            @RequestParam Integer cantidad) {
        return ResponseEntity.ok(productoService.actualizarStock(id, cantidad));
    }
} 