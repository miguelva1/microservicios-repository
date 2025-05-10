package com.example.msordenes.client;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioResponse {
    private String id;
    private String username;
    private String email;
    private List<String> roles;
} 