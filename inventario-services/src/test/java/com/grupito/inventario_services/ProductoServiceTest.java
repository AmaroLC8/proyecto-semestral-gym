package com.grupito.inventario_services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.grupito.inventario_services.exception.BadRequestException;
import com.grupito.inventario_services.exception.ResourceNotFoundException;
import com.grupito.inventario_services.model.Producto;
import com.grupito.inventario_services.repository.ProductoRepository;
import com.grupito.inventario_services.service.ProductoService;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    // ============================================================
    // TESTS CRUD BASICOS
    // ============================================================

    @Test
    public void testGuardarProducto_Exitoso() {
        Producto producto = Producto.builder()
                .nombre("Bicicleta Estatica")
                .stock(10)
                .precio(150000.0)
                .categoria("EQUIPAMIENTO")
                .build();

        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.guardar(producto);

        assertNotNull(resultado);
        assertEquals("Bicicleta Estatica", resultado.getNombre());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    public void testListarProductos() {
        Producto p1 = Producto.builder().id(1L).nombre("Pesa 10kg").stock(20).precio(25000.0).categoria("EQUIPAMIENTO").build();
        Producto p2 = Producto.builder().id(2L).nombre("Proteina Whey").stock(50).precio(35000.0).categoria("SUPLEMENTO").build();
        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Producto> resultado = productoService.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Producto producto = Producto.builder().id(id).nombre("Mancuerna").stock(15).precio(12000.0).categoria("EQUIPAMIENTO").build();
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Mancuerna", resultado.getNombre());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productoService.obtenerPorId(id));
    }

    @Test
    public void testEliminarProducto() {
        Long id = 1L;
        doNothing().when(productoRepository).deleteById(id);

        productoService.eliminar(id);

        verify(productoRepository, times(1)).deleteById(id);
    }

    // ============================================================
    // TESTS REGLAS DE NEGOCIO
    // ============================================================

    /**
     * REGLA 1: El stock no puede ser negativo.
     */
    @Test
    public void testRegla1_StockNegativo_LanzaExcepcion() {
        Producto producto = Producto.builder()
                .nombre("Banda Elastica")
                .stock(-5)
                .precio(8000.0)
                .categoria("ACCESORIO")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> productoService.guardar(producto));
        assertTrue(ex.getMessage().contains("stock debe estar entre"));
    }

    /**
     * REGLA 1 (borde superior): El stock no puede superar las 10.000 unidades.
     */
    @Test
    public void testRegla1_StockSuperiorAlMaximo_LanzaExcepcion() {
        Producto producto = Producto.builder()
                .nombre("Cuerda de Saltar")
                .stock(15000)
                .precio(5000.0)
                .categoria("ACCESORIO")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> productoService.guardar(producto));
        assertTrue(ex.getMessage().contains("stock debe estar entre"));
    }

    /**
     * REGLA 2: El precio debe ser mayor a 0.
     */
    @Test
    public void testRegla2_PrecioCero_LanzaExcepcion() {
        Producto producto = Producto.builder()
                .nombre("Guantes Box")
                .stock(10)
                .precio(0.0)
                .categoria("ACCESORIO")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> productoService.guardar(producto));
        assertTrue(ex.getMessage().contains("precio debe ser mayor a 0"));
    }

    /**
     * REGLA 3: La categoria debe ser una de las validas.
     */
    @Test
    public void testRegla3_CategoriaInvalida_LanzaExcepcion() {
        Producto producto = Producto.builder()
                .nombre("Zapatos Running")
                .stock(20)
                .precio(50000.0)
                .categoria("ZAPATILLAS")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> productoService.guardar(producto));
        assertTrue(ex.getMessage().contains("Categoria invalida"));
    }
}