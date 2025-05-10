package com.example.msordenes.config;

import com.example.msordenes.client.AuthClient;
import com.example.msordenes.client.UsuarioResponse;
import com.example.msordenes.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;



@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthClient authClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtFromRequest(request);
        if (token != null) {
            try {
                ResponseEntity<JwtResponse> responseEntity = authClient.validateToken("Bearer " + token);

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    JwtResponse jwtResponse = responseEntity.getBody();
                    System.out.println("JWT RESPONSE: " + jwtResponse); // ← LOG CRÍTICO

                    List<SimpleGrantedAuthority> authorities = jwtResponse.getRoles().stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    System.out.println("Authorities: " + authorities);
                    System.out.println("Autenticado: " + SecurityContextHolder.getContext().getAuthentication());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(jwtResponse.getUsername(), null, authorities);;

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception e) {
                e.printStackTrace(); // para desarrollo
                logger.error("Error validando token", e); // más profesional
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer "))
                ? bearerToken.substring(7)
                : null;
    }
}
