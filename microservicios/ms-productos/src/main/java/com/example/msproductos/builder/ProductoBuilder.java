package com.example.msproductos.builder;

import com.example.msproductos.model.Producto;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Implementación del patrón Builder para la construcción de productos.
 * Permite crear productos de manera fluida y con validaciones.
 */
@NoArgsConstructor
public class ProductoBuilder {
    private Producto producto;

    /**
     * Inicia la construcción de un nuevo producto.
     * @return this para encadenar llamadas
     */
    public ProductoBuilder nuevo() {
        producto = new Producto();
        producto.setActivo(true);
        return this;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre Nombre del producto
     * @return this para encadenar llamadas
     */
    public ProductoBuilder conNombre(String nombre) {
        producto.setNombre(nombre);
        return this;
    }

    /**
     * Establece la descripción del producto.
     * @param descripcion Descripción del producto
     * @return this para encadenar llamadas
     */
    public ProductoBuilder conDescripcion(String descripcion) {
        producto.setDescripcion(descripcion);
        return this;
    }

    /**
     * Establece el precio del producto.
     * @param precio Precio del producto
     * @return this para encadenar llamadas
     */
    public ProductoBuilder conPrecio(BigDecimal precio) {
        producto.setPrecio(precio);
        return this;
    }

    /**
     * Establece el stock del producto.
     * @param stock Cantidad en stock
     * @return this para encadenar llamadas
     */
    public ProductoBuilder conStock(Integer stock) {
        producto.setStock(stock);
        return this;
    }

    /**
     * Establece la categoría del producto.
     * @param categoria Categoría del producto
     * @return this para encadenar llamadas
     */
    public ProductoBuilder conCategoria(String categoria) {
        producto.setCategoria(categoria);
        return this;
    }

    /**
     * Establece la URL de la imagen del producto.
     * @param imagenUrl URL de la imagen
     * @return this para encadenar llamadas
     */
    public ProductoBuilder conImagenUrl(String imagenUrl) {
        producto.setImagenUrl(imagenUrl);
        return this;
    }

    /**
     * Establece el estado activo del producto.
     * @param activo Estado activo
     * @return this para encadenar llamadas
     */
    public ProductoBuilder activo(Boolean activo) {
        producto.setActivo(activo);
        return this;
    }

    /**
     * Construye y valida el producto.
     * @return Producto construido
     * @throws IllegalStateException si faltan campos requeridos
     */
    public Producto build() {
        validarProducto();
        return producto;
    }

    /**
     * Valida que el producto tenga todos los campos requeridos.
     * @throws IllegalStateException si faltan campos requeridos
     */
    private void validarProducto() {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalStateException("El nombre del producto es requerido");
        }
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("El precio del producto debe ser mayor o igual a 0");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalStateException("El stock del producto debe ser mayor o igual a 0");
        }
        if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            throw new IllegalStateException("La categoría del producto es requerida");
        }
    }
} 