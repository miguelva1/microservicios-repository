package com.example.msordenes.repository;

import com.example.msordenes.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuarioId(String usuarioId);
} 