package com.example.msauth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa a un usuario en el sistema
 * Guarda la info básica del usuario y sus roles
 * 
 * @author NTT DATA
 * @version 1.0
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    /**
     * ID único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario único para el inicio de sesión.
     * Debe tener entre 3 y 50 caracteres.
     */
    @NotBlank(message = "El nombre de usuario es requerido")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Contraseña del usuario.
     * Debe tener entre 6 y 100 caracteres.
     */
    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    @Column(nullable = false)
    private String password;

    /**
     * Correo electrónico del usuario.
     * Debe ser una dirección de correo válida.
     */
    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Roles asignados al usuario.
     * Un usuario puede tener múltiples roles.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();
} 