package com.example.msproductos.service;

import com.example.msproductos.dto.ProductoDTO;
import com.example.msproductos.model.Producto;

import java.util.List;

// Interfaz que define las operaciones del servicio de productos
// Maneja la lógica de negocio relacionada con los productos
public interface ProductoService {
    // Crea un nuevo producto
    // @param productoDTO Datos del producto a crear
    // @return Producto creado
    Producto crearProducto(ProductoDTO productoDTO);

    // Obtiene todos los productos activos
    // @return Lista de productos
    List<Producto> obtenerProductos();

    // Obtiene un producto por su ID
    // @param id ID del producto
    // @return Producto encontrado
    Producto obtenerProducto(Long id);

    // Actualiza un producto existente
    // @param id ID del producto a actualizar
    // @param productoDTO Nuevos datos del producto
    // @return Producto actualizado
    Producto actualizarProducto(Long id, ProductoDTO productoDTO);

    // Elimina un producto (marcándolo como inactivo)
    // @param id ID del producto a eliminar
    void eliminarProducto(Long id);

    // Verifica si existe un producto con el ID dado
    // @param id ID del producto a verificar
    // @return true si existe, false en caso contrario
    boolean existeProducto(Long id);

    // Actualiza el stock de un producto
    // @param id ID del producto
    // @param cantidad Cantidad a actualizar (positiva para aumentar, negativa para disminuir)
    // @return Producto actualizado
    Producto actualizarStock(Long id, Integer cantidad);
} 