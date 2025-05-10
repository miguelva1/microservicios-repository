package com.example.msproductos.repository;

import com.example.msproductos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repositorio para gestionar productos en la base de datos
// Proporciona métodos para operaciones CRUD y consultas personalizadas
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Busca todos los productos activos
    List<Producto> findByActivoTrue();

    // Busca productos por categoría
    List<Producto> findByCategoriaAndActivoTrue(String categoria);

    // Busca productos por nombre (búsqueda parcial)
    List<Producto> findByNombreContainingAndActivoTrue(String nombre);

    // Verifica si existe un producto con el nombre dado
    boolean existsByNombre(String nombre);

    // Busca productos por rango de precios
    List<Producto> findByPrecioBetweenAndActivoTrue(Double precioMin, Double precioMax);
} 