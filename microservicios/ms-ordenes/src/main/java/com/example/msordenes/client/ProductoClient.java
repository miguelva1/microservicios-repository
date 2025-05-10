package com.example.msordenes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Cliente Feign para comunicarse con el microservicio de productos
// Permite obtener información de productos y actualizar su stock
@FeignClient(name = "ms-productos", url = "${app.services.productos-url}")
public interface ProductoClient {
    // Obtiene un producto por su ID
    // @param id ID del producto
    // @return ResponseEntity con la información del producto
    @GetMapping("/api/productos/{id}")
    ResponseEntity<ProductoResponse> obtenerProducto(@PathVariable Long id);

    // Actualiza el stock de un producto
    // @param id ID del producto
    // @param cantidad Cantidad a actualizar (positiva para aumentar, negativa para disminuir)
    // @return ResponseEntity con el resultado de la operación
    @PutMapping("/api/productos/{id}/stock")
    ResponseEntity<?> actualizarStock(@PathVariable Long id, @RequestParam Integer cantidad);
}
