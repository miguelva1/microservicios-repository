package com.example.msauth.controller;

import com.example.msauth.dto.JwtResponse;
import com.example.msauth.dto.LoginRequest;
import com.example.msauth.dto.MessageResponse;
import com.example.msauth.dto.SignupRequest;
import com.example.msauth.model.Usuario;
import com.example.msauth.security.JwtTokenProvider;
import com.example.msauth.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// Controlador que maneja la autenticación y registro
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtTokenProvider tokenProvider;

    // Endpoint para iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // Autentica al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Genera el token JWT
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        // Obtiene los datos del usuario
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Obtiene el usuario completo de la base de datos
        Usuario usuario = usuarioService.findByEmail(userDetails.getUsername());

        // Devuelve la respuesta con el token y los datos del usuario
        return ResponseEntity.ok(new JwtResponse(jwt,
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                roles));
    }

    // Endpoint para registrarse
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {
        if (usuarioService.existePorUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: El nombre de usuario ya está en uso"));
        }

        if (usuarioService.existePorEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: El email ya está en uso"));
        }

        Usuario usuario = usuarioService.crearUsuario(request);
        return ResponseEntity.ok(new MessageResponse("Usuario registrado exitosamente"));
    }

    // Endpoint para validar un token
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (tokenProvider.validateToken(token)) {
                String username = tokenProvider.getUsernameFromJWT(token);
                Usuario usuario = usuarioService.findByEmail(username); // O findByUsername si el token contiene el username

                if (usuario == null) {
                    return ResponseEntity.status(404).body(new MessageResponse("Usuario no encontrado"));
                }

                List<String> roles = usuario.getRoles().stream()
                        .map(role -> role.getNombre().name())
                        .collect(Collectors.toList());

                return ResponseEntity.ok(new JwtResponse(
                        token,
                        usuario.getId(),
                        usuario.getUsername(),
                        usuario.getEmail(),
                        roles
                ));
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Token inválido"));

    }
}