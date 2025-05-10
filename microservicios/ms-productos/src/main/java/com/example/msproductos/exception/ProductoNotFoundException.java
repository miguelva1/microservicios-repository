package com.example.msproductos.exception;

// Excepción personalizada para cuando no se encuentra un producto
// Se lanza cuando se intenta acceder a un producto que no existe
public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(String message) {
        super(message);
    }
} 