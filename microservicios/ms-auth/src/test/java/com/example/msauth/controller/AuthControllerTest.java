package com.example.msauth.controller;

import com.example.msauth.dto.LoginRequest;
import com.example.msauth.dto.SignupRequest;
import com.example.msauth.model.Rol;
import com.example.msauth.model.Usuario;
import com.example.msauth.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// Clase de pruebas para el controlador de autenticación
// Prueba los endpoints de login, registro y validación de token
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    // Prueba el registro de un usuario válido
    // Debe devolver un código 200 (OK)
    @Test
    public void registerUser_ValidUser_ReturnsOk() throws Exception {
        // Preparar datos de prueba
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("jose123");
        signupRequest.setEmail("jose@gmail.com");
        signupRequest.setPassword("password123");
        Set<String> roles = new HashSet<>();
        roles.add("user");
        signupRequest.setRoles(roles);

        // Configurar el comportamiento del mock
        Usuario usuario = new Usuario();
        usuario.setUsername("jose123");
        usuario.setEmail("jose@gmail.com");
        Set<Rol> userRoles = new HashSet<>();
        Rol userRole = new Rol();
        userRole.setNombre(Rol.ERol.ROLE_USER);
        userRoles.add(userRole);
        usuario.setRoles(userRoles);

        when(usuarioService.crearUsuario(any(SignupRequest.class))).thenReturn(usuario);

        // Ejecutar la prueba
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Usuario registrado exitosamente"));
    }

    // Prueba el login con credenciales válidas
    // Debe devolver un código 200 (OK) y un token JWT
    @Test
    public void login_ValidCredentials_ReturnsToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jose@gmail.com");
        loginRequest.setPassword("password123");

        // Crear un UserDetails con los roles necesarios
        UserDetails userDetails = new User(
            "jose@gmail.com",
            "password123",
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // Crear la autenticación con el UserDetails
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        
        // Configurar el mock del AuthenticationManager
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Configurar el mock del UsuarioService
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("jose123");
        usuario.setEmail("jose@gmail.com");
        Set<Rol> userRoles = new HashSet<>();
        Rol userRole = new Rol();
        userRole.setNombre(Rol.ERol.ROLE_USER);
        userRoles.add(userRole);
        usuario.setRoles(userRoles);
        when(usuarioService.findByEmail(anyString())).thenReturn(usuario);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("jose123"))
                .andExpect(jsonPath("$.email").value("jose@gmail.com"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    // Prueba el registro fallido por email existente
    @Test
    public void registerUser_EmailExists_ReturnsBadRequest() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("juanperez");
        signupRequest.setEmail("juan.perez@gmail.com");
        signupRequest.setPassword("password123");
        signupRequest.setRoles(Set.of("user"));

        when(usuarioService.existePorUsername("juanperez")).thenReturn(false);
        when(usuarioService.existePorEmail("juan.perez@gmail.com")).thenReturn(true);

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: El email ya está en uso"));
    }

    // Prueba la validación de token válido
    @Test
    public void validateToken_Valid_ReturnsOk() throws Exception {
        String token = "Bearer valid.jwt.token";
        // Simula que el token es válido
        when(usuarioService.findByEmail(anyString())).thenReturn(new Usuario());
        // No se mockea el tokenProvider porque se usa el real en el filtro
        mockMvc.perform(post("/auth/validate")
                .header("Authorization", token))
                .andExpect(status().isOk());
    }
} 