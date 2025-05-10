package com.example.msauth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO para recibir los datos de login
@Data
public class LoginRequest {
    // Usuario o email para iniciar sesión
    @NotBlank(message = "El username es requerido")
    private String username;

    // Contraseña del usuario
    @NotBlank(message = "La contraseña es requerida")
    private String password;
} 