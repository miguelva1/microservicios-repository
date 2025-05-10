package com.example.msproductos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

// Cliente Feign para comunicarse con el microservicio de autenticación
// Permite validar tokens JWT y obtener información del usuario
@FeignClient(name = "ms-auth", url = "${app.services.auth-url}")
public interface AuthClient {
    // Valida un token JWT con el microservicio de autenticación
    // @param token Token JWT a validar (en formato "Bearer <token>")
    // @return ResponseEntity con la información del usuario si el token es válido
    @GetMapping("/auth/validate")
    ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token);
}