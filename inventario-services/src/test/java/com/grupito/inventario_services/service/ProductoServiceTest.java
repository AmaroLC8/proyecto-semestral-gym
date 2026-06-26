package com.grupito.inventario_services.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.grupito.inventario_services.exception.ResourceNotFoundException;
import com.grupito.inventario_services.model.Producto;
import com.grupito.inventario_services.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    public void testGuardarProducto_Exitoso() {
        Producto producto = Producto.builder()
                .nombre("Proteína Whey")
                .stock(50)
                .precio(29990.0)
                .categoria("Suplementos")
                .build();

        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.guardar(producto);

        assertNotNull(resultado);
        assertEquals("Proteína Whey", resultado.getNombre());
        assertEquals(50, resultado.getStock());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    public void testListarProductos() {
        Producto p1 = Producto.builder().id(1L).nombre("Proteína").build();
        Producto p2 = Producto.builder().id(2L).nombre("Creatina").build();
        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Producto> resultado = productoService.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Producto producto = Producto.builder().id(id).nombre("Proteína").build();
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Proteína", resultado.getNombre());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> productoService.obtenerPorId(id));
        assertTrue(exception.getMessage().contains("Producto no encontrado"));
    }

    @Test
    public void testEliminarProducto() {
        Long id = 1L;
        doNothing().when(productoRepository).deleteById(id);

        productoService.eliminar(id);

        verify(productoRepository, times(1)).deleteById(id);
    }
}