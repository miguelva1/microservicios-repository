package com.example.msauth.dto;

import lombok.Data;

import java.util.List;

// DTO para devolver la respuesta del login
// Incluye el token JWT y la info del usuario
@Data
public class JwtResponse {
    // Token JWT generado
    private String token;

    // Tipo de token (siempre "Bearer")
    private String type = "Bearer";

    // ID del usuario
    private Long id;

    // Nombre de usuario
    private String username;

    // Email del usuario
    private String email;

    // Lista de roles del usuario
    private List<String> roles;

    // Constructor con los datos básicos
    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
} 