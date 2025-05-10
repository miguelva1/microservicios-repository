package com.example.msproductos.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

// Clase para representar una respuesta de error
// Contiene información detallada sobre el error ocurrido
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    // Código de estado HTTP del error
    private int status;

    // Fecha y hora en que ocurrió el error
    private Date timestamp;

    // Mensaje descriptivo del error
    private String message;

    // Descripción detallada del error
    private String details;
} 