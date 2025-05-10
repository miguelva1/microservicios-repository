package com.example.mseurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Clase principal que inicia el servidor Eureka
 * Se encarga de registrar y descubrir los microservicios
 */
@SpringBootApplication
@EnableEurekaServer
public class MsEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsEurekaServerApplication.class, args);
    }
} 