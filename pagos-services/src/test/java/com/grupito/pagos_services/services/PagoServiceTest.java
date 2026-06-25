package com.grupito.pagos_services.services;

import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.repository.PagoRepository;
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
class PagoServiceTest {

    @Mock private PagoRepository pagoRepository;
    @InjectMocks private PagoService pagoService;
    private Pago pagoFalso;

    @BeforeEach
    void setUp() {
        pagoFalso = new Pago();
        pagoFalso.setId(1L);
        pagoFalso.setMonto(15000.0);
    }

    @Test
    @DisplayName("Búsqueda exitosa")
    void buscarPorId_Exito() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoFalso));
        Pago resultado = pagoService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals(15000.0, resultado.getMonto());
    }

    @Test
    @DisplayName("Búsqueda fallida retorna null")
    void buscarPorId_Falla() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());
        assertNull(pagoService.obtenerPorId(99L));
    }
}