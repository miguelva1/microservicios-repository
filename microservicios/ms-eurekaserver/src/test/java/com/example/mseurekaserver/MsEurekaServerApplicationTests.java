package com.example.mseurekaserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "eureka.client.register-with-eureka=false",
    "eureka.client.fetch-registry=false"
})
class MsEurekaServerApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se carga correctamente
    }

    @Test
    void applicationStarts() {
        // Verifica que la aplicación inicia correctamente
        MsEurekaServerApplication.main(new String[]{});
    }
} 