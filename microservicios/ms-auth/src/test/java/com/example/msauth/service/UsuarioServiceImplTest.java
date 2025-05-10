package com.example.msauth.service;

import com.example.msauth.dto.SignupRequest;
import com.example.msauth.model.Rol;
import com.example.msauth.model.Usuario;
import com.example.msauth.repository.RolRepository;
import com.example.msauth.repository.UsuarioRepository;
import com.example.msauth.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UsuarioServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuario_ShouldReturnUsuario() {
        SignupRequest request = new SignupRequest();
        request.setUsername("juanperez");
        request.setEmail("juan.perez@gmail.com");
        request.setPassword("password123");
        request.setRoles(Set.of("user"));

        Rol rolUser = new Rol();
        rolUser.setNombre(Rol.ERol.ROLE_USER);
        when(rolRepository.findByNombre(Rol.ERol.ROLE_USER)).thenReturn(Optional.of(rolUser));
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");

        Usuario usuario = new Usuario();
        usuario.setUsername("juanperez");
        usuario.setEmail("juan.perez@gmail.com");
        usuario.setPassword("hashed");
        usuario.setRoles(new HashSet<>(Set.of(rolUser)));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.crearUsuario(request);
        assertNotNull(result);
        assertEquals("juanperez", result.getUsername());
        assertEquals("juan.perez@gmail.com", result.getEmail());
        assertEquals("hashed", result.getPassword());
        assertTrue(result.getRoles().stream().anyMatch(r -> r.getNombre() == Rol.ERol.ROLE_USER));
    }

    @Test
    void existePorUsername_ShouldReturnTrue() {
        when(usuarioRepository.existsByUsername("juanperez")).thenReturn(true);
        assertTrue(usuarioService.existePorUsername("juanperez"));
    }

    @Test
    void existePorEmail_ShouldReturnTrue() {
        when(usuarioRepository.existsByEmail("juan.perez@gmail.com")).thenReturn(true);
        assertTrue(usuarioService.existePorEmail("juan.perez@gmail.com"));
    }

    @Test
    void findByEmail_ShouldReturnUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juanperez");
        usuario.setEmail("juan.perez@gmail.com");
        when(usuarioRepository.findByUsername("juan.perez@gmail.com")).thenReturn(Optional.of(usuario));
        Usuario result = usuarioService.findByEmail("juan.perez@gmail.com");
        assertNotNull(result);
        assertEquals("juan.perez@gmail.com", result.getEmail());
    }
} 