package com.example.msauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Clase principal que arranca el microservicio de autenticación
 * Se encarga de generar y validar tokens JWT y manejar usuarios
 * 
 * @author NTT DATA
 * @version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MsAuthApplication {
    /**
     * Método que inicia la aplicación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(MsAuthApplication.class, args);
    }
} 