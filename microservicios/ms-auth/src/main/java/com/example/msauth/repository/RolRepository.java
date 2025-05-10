package com.example.msauth.repository;

import com.example.msauth.model.Rol;
import com.example.msauth.model.Rol.ERol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repositorio para manejar los roles en la base de datos
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    // Busca un rol por su nombre (USER, ADMIN, etc)
    Optional<Rol> findByNombre(ERol nombre);
} 