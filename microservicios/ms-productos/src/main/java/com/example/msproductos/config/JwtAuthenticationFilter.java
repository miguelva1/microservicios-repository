package com.example.msproductos.config;

import com.example.msproductos.client.AuthClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Filtro de autenticación JWT
// Intercepta todas las peticiones para validar el token JWT
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthClient authClient;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);  // Logger agregado

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtFromRequest(request);  // Obtiene el token del encabezado
        if (token != null) {
            try {
                // Si decides seguir con la validación remota, usa este bloque
                // authClient.validateToken("Bearer " + token); // Validación remota (opcional)

                // Luego, validamos el token localmente
                if (!jwtTokenProvider.validateToken(token)) {
                    throw new ServletException("Token inválido");
                }

                Authentication authentication = jwtTokenProvider.getAuthentication(token); // Parsing y validación del JWT
                SecurityContextHolder.getContext().setAuthentication(authentication);  // Establece la autenticación

                logger.info("Autenticación establecida para usuario: {}, Roles: {}",
                        authentication.getName(),
                        authentication.getAuthorities());

                logger.info("Token validado y autenticación establecida");  // Log de éxito

            } catch (Exception e) {
                logger.error("Token inválido: {}", e.getMessage());  // Log de error
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Establece código HTTP 401
                response.getWriter().write("Token inválido: " + e.getMessage());  // Respuesta con mensaje de error
                return;  // Detiene el flujo si el token es inválido
            }
        }

        filterChain.doFilter(request, response);  // Continúa el filtro
    }

    // Extrae el token JWT del encabezado Authorization
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Elimina "Bearer " y extrae el token
        }
        return null;  // Retorna null si no existe o no es un Bearer token
    }
}
