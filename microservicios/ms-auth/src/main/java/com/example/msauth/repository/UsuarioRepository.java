package com.example.msauth.repository;

import com.example.msauth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repositorio para manejar los usuarios en la base de datos
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca un usuario por su nombre de usuario
    Optional<Usuario> findByUsername(String username);

    // Verifica si existe un usuario con ese nombre
    Boolean existsByUsername(String username);

    // Verifica si existe un usuario con ese email
    Boolean existsByEmail(String email);
} 