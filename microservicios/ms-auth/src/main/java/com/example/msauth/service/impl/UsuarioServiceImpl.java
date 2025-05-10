package com.example.msauth.service.impl;

import com.example.msauth.dto.SignupRequest;
import com.example.msauth.model.Rol;
import com.example.msauth.model.Usuario;
import com.example.msauth.repository.RolRepository;
import com.example.msauth.repository.UsuarioRepository;
import com.example.msauth.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario crearUsuario(SignupRequest request) {
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Rol> roles = new HashSet<>();

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            Rol userRole = rolRepository.findByNombre(Rol.ERol.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(userRole);
        } else {
            request.getRoles().forEach(role -> {
                switch (role) {
                    case "admin" -> roles.add(rolRepository.findByNombre(Rol.ERol.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado.")));
                    case "superadmin" -> roles.add(rolRepository.findByNombre(Rol.ERol.ROLE_SUPERADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado.")));
                    default -> roles.add(rolRepository.findByNombre(Rol.ERol.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado.")));
                }
            });
        }

        usuario.setRoles(roles);
        return usuarioRepository.save(usuario);
    }

    @Override
    public boolean existePorUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Usuario registerUser(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }
}
