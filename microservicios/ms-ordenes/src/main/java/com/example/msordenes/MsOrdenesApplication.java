package com.example.msordenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsOrdenesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsOrdenesApplication.class, args);
    }
} 