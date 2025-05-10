package com.example.msauth.service;

import com.example.msauth.dto.SignupRequest;
import com.example.msauth.model.Usuario;

public interface UsuarioService {
    Usuario crearUsuario(SignupRequest request);
    boolean existePorUsername(String username);
    boolean existePorEmail(String email);
    Usuario registerUser(Usuario usuario);
    Usuario findByEmail(String email);
}
