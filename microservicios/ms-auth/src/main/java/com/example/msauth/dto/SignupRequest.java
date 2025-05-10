package com.example.msauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

// DTO para recibir los datos de registro
@Data
public class SignupRequest {
    // Nombre de usuario para el registro
    @NotBlank(message = "El username es requerido")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    private String username;

    // Email del usuario
    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    private String email;

    // Contraseña del usuario
    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    // Roles que se le asignarán al usuario
    private Set<String> roles;
} 