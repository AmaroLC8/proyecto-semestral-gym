package com.grupito.inventario_services.service;

import com.grupito.inventario_services.model.Inventarios;
import com.grupito.inventario_services.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock private InventarioRepository inventarioRepository;
    @InjectMocks private InventarioService inventarioService;
    private Inventarios inventarioFalso;

    @BeforeEach
    void setUp() {
        inventarioFalso = new Inventarios();
        inventarioFalso.setId(1L);
    }

    @Test
    @DisplayName("Buscar inventario exitoso")
    void buscarPorId_Exito() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioFalso));
        Inventarios resultado = inventarioService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }
}