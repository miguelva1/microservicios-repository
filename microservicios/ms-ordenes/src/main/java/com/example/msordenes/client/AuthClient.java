package com.example.msordenes.client;

import com.example.msordenes.dto.JwtResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

// Cliente Feign para comunicarse con el microservicio de autenticación
// Permite validar tokens JWT y obtener información del usuario
@FeignClient(name = "ms-auth", url = "${app.services.auth-url}")
public interface AuthClient {

    @GetMapping("/auth/validate")
    ResponseEntity<JwtResponse> validateToken(@RequestHeader("Authorization") String token);
}