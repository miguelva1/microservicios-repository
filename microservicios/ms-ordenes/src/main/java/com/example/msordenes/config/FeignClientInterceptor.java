package com.example.msordenes.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // Obtener el token desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificar si la autenticación está presente
        if (authentication != null && authentication.getCredentials() != null) {
            String token = (String) authentication.getCredentials();

            if (token != null && !token.isEmpty()) {
                // Agregar el token JWT al encabezado Authorization de la solicitud Feign
                template.header("Authorization", "Bearer " + token);
            }
        } else {
            // Aquí podrías agregar un log si no hay autenticación
            System.out.println("No hay autenticación en el SecurityContext");
        }
    }
}