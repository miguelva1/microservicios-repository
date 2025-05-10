package com.example.msauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// DTO para devolver mensajes simples
// Se usa para respuestas de éxito o error
@Data
@AllArgsConstructor
public class MessageResponse {
    // Mensaje a devolver
    private String message;
} 