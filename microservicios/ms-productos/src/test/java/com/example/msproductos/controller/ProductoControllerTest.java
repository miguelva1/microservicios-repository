package com.example.msproductos.controller;

import com.example.msproductos.dto.ProductoDTO;
import com.example.msproductos.model.Producto;
import com.example.msproductos.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.example.msproductos.config.TestSecurityConfig;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Clase de pruebas para el controlador de productos
// Prueba los endpoints CRUD y la validación de roles
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    // Prueba la creación de un producto con rol ADMIN
    // Debe devolver un código 200 (OK) y el producto creado
    @Test
    @WithMockUser(roles = "ADMIN")
    public void crearProducto_ValidProduct_ReturnsOk() throws Exception {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre("Test Product");
        productoDTO.setDescripcion("Test Description");
        productoDTO.setPrecio(new BigDecimal("100.00"));
        productoDTO.setStock(10);
        productoDTO.setCategoria("Electrónicos");
        productoDTO.setImagenUrl("http://example.com/image.jpg");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setCategoria(productoDTO.getCategoria());
        producto.setImagenUrl(productoDTO.getImagenUrl());
        producto.setActivo(true);

        when(productoService.crearProducto(any(ProductoDTO.class))).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Test Product"))
                .andExpect(jsonPath("$.categoria").value("Electrónicos"))
                .andExpect(jsonPath("$.imagenUrl").value("http://example.com/image.jpg"));
    }

    // Prueba la creación de un producto con rol USER
    // Debe devolver un código 403 (Forbidden)
    @Test
    @WithMockUser
    public void crearProducto_UnauthorizedUser_ReturnsForbidden() throws Exception {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre("Test Product");
        productoDTO.setDescripcion("Test Description");
        productoDTO.setPrecio(new BigDecimal("100.00"));
        productoDTO.setStock(10);
        productoDTO.setCategoria("Electrónicos");
        productoDTO.setImagenUrl("http://example.com/image.jpg");

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoDTO)))
                .andExpect(status().isForbidden());
    }

    // Prueba la obtención de todos los productos
    // Debe devolver un código 200 (OK) y la lista de productos
    @Test
    @WithMockUser
    public void obtenerProductos_ReturnsProductList() throws Exception {
        List<Producto> productos = Arrays.asList(
            new Producto(1L, "Product 1", "Description 1", new BigDecimal("100.00"), 10, "Electrónicos", "http://example.com/image1.jpg", true),
            new Producto(2L, "Product 2", "Description 2", new BigDecimal("200.00"), 20, "Ropa", "http://example.com/image2.jpg", true)
        );

        when(productoService.obtenerProductos()).thenReturn(productos);

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Product 1"))
                .andExpect(jsonPath("$[0].categoria").value("Electrónicos"))
                .andExpect(jsonPath("$[1].nombre").value("Product 2"))
                .andExpect(jsonPath("$[1].categoria").value("Ropa"));
    }

    // Prueba la obtención de un producto por ID
    // Debe devolver un código 200 (OK) y el producto
    @Test
    @WithMockUser
    public void obtenerProducto_ValidId_ReturnsProduct() throws Exception {
        Producto producto = new Producto(1L, "Test Product", "Test Description", new BigDecimal("100.00"), 10, "Electrónicos", "http://example.com/image.jpg", true);

        when(productoService.obtenerProducto(1L)).thenReturn(producto);

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Product"))
                .andExpect(jsonPath("$.categoria").value("Electrónicos"))
                .andExpect(jsonPath("$.imagenUrl").value("http://example.com/image.jpg"));
    }

    // Prueba la actualización de un producto con rol ADMIN
    // Debe devolver un código 200 (OK) y el producto actualizado
    @Test
    @WithMockUser(roles = "ADMIN")
    public void actualizarProducto_ValidProduct_ReturnsOk() throws Exception {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre("Updated Product");
        productoDTO.setDescripcion("Updated Description");
        productoDTO.setPrecio(new BigDecimal("200.00"));
        productoDTO.setStock(20);
        productoDTO.setCategoria("Ropa");
        productoDTO.setImagenUrl("http://example.com/updated-image.jpg");

        Producto producto = new Producto(1L, "Updated Product", "Updated Description", new BigDecimal("200.00"), 20, "Ropa", "http://example.com/updated-image.jpg", true);

        when(productoService.actualizarProducto(any(Long.class), any(ProductoDTO.class))).thenReturn(producto);

        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Updated Product"))
                .andExpect(jsonPath("$.categoria").value("Ropa"))
                .andExpect(jsonPath("$.imagenUrl").value("http://example.com/updated-image.jpg"));
    }

    // Prueba la eliminación de un producto con rol ADMIN
    // Debe devolver un código 200 (OK)
    @Test
    @WithMockUser(roles = "ADMIN")
    public void eliminarProducto_ValidId_ReturnsOk() throws Exception {
        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isOk());
    }
} 