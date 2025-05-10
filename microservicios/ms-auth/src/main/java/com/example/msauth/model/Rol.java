package com.example.msauth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Clase que representa los roles en el sistema
// Por ejemplo: USER, ADMIN, SUPERADMIN
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    // ID único del rol
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del rol (USER, ADMIN, etc)
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERol nombre;

    // Enum con los tipos de roles disponibles
    public enum ERol {
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_SUPERADMIN
    }
} 