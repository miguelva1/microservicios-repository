package com.example.msauth.controller;

import com.example.msauth.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final UsuarioService usuarioService;

    @GetMapping("/superadmin")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<?> superadminEndpoint() {
        return ResponseEntity.ok("Superadmin endpoint accessed successfully");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<?> adminEndpoint() {
        return ResponseEntity.ok("Admin endpoint accessed successfully");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<?> userEndpoint() {
        return ResponseEntity.ok("User endpoint accessed successfully");
    }
} 