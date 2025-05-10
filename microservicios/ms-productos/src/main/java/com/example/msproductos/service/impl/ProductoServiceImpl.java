package com.example.msproductos.service.impl;

import com.example.msproductos.builder.ProductoBuilder;
import com.example.msproductos.dto.ProductoDTO;
import com.example.msproductos.exception.ProductoNotFoundException;
import com.example.msproductos.model.Producto;
import com.example.msproductos.repository.ProductoRepository;
import com.example.msproductos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Implementación del servicio de productos
// Contiene la lógica de negocio para gestionar productos
@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    // Crea un nuevo producto a partir de un DTO
    @Override
    @Transactional
    public Producto crearProducto(ProductoDTO productoDTO) {
        Producto producto = new ProductoBuilder()
            .nuevo()
            .conNombre(productoDTO.getNombre())
            .conDescripcion(productoDTO.getDescripcion())
            .conPrecio(productoDTO.getPrecio())
            .conStock(productoDTO.getStock())
            .conCategoria(productoDTO.getCategoria())
            .conImagenUrl(productoDTO.getImagenUrl())
            .activo(true)
            .build();
        
        return productoRepository.save(producto);
    }

    // Obtiene todos los productos activos
    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerProductos() {
        return productoRepository.findByActivoTrue();
    }

    // Obtiene un producto por su ID
    @Override
    @Transactional(readOnly = true)
    public Producto obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));
    }

    // Actualiza un producto existente
    @Override
    @Transactional
    public Producto actualizarProducto(Long id, ProductoDTO productoDTO) {
        Producto productoExistente = obtenerProducto(id);
        
        Producto productoActualizado = new ProductoBuilder()
            .nuevo()
            .conNombre(productoDTO.getNombre())
            .conDescripcion(productoDTO.getDescripcion())
            .conPrecio(productoDTO.getPrecio())
            .conStock(productoDTO.getStock())
            .conCategoria(productoDTO.getCategoria())
            .conImagenUrl(productoDTO.getImagenUrl())
            .activo(productoExistente.getActivo())
            .build();
        
        productoActualizado.setId(id);
        return productoRepository.save(productoActualizado);
    }

    // Elimina un producto (marcándolo como inactivo)
    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = obtenerProducto(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    // Verifica si existe un producto con el ID dado
    @Override
    @Transactional(readOnly = true)
    public boolean existeProducto(Long id) {
        return productoRepository.existsById(id);
    }

    // Actualiza el stock de un producto
    @Override
    @Transactional
    public Producto actualizarStock(Long id, Integer cantidad) {
        Producto producto = obtenerProducto(id);
        int nuevoStock = producto.getStock() + cantidad;
        
        if (nuevoStock < 0) {
            throw new IllegalArgumentException("No hay suficiente stock disponible");
        }
        
        Producto productoActualizado = new ProductoBuilder()
            .nuevo()
            .conNombre(producto.getNombre())
            .conDescripcion(producto.getDescripcion())
            .conPrecio(producto.getPrecio())
            .conStock(nuevoStock)
            .conCategoria(producto.getCategoria())
            .conImagenUrl(producto.getImagenUrl())
            .activo(producto.getActivo())
            .build();
        
        productoActualizado.setId(id);
        return productoRepository.save(productoActualizado);
    }
} 