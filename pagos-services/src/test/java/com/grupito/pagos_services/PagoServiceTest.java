package com.grupito.pagos_services;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.repository.PagoRepository;
import com.grupito.pagos_services.services.PagoService;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    @Test
    public void testProcesarYGuardarPago_CalculoCorrecto() {
        // GIVEN: Un pago de $10.000 con 20% de descuento.
        Pago pagoEntrada = new Pago();
        pagoEntrada.setValorNeto(10000);
        pagoEntrada.setDescuento(20);
        // 20% de 10000 = 2000. Subtotal = 8000. IVA (19%) = 1520. Total = 9520.

        when(pagoRepository.save(any(Pago.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        Pago resultado = pagoService.procesarYGuardarPago(pagoEntrada);

        // THEN
        assertEquals(1520, resultado.getIva());
        assertEquals(9520, resultado.getTotalPagar());
        assertNotNull(resultado.getFecha());
        verify(pagoRepository, times(1)).save(pagoEntrada);
    }

    @Test
    public void testObtenerPago_NoEncontrado() {
        // GIVEN
        Long idInexistente = 99L;
        when(pagoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN
        Exception exception = assertThrows(RuntimeException.class, () -> pagoService.obtenerPorId(idInexistente));
        assertEquals("Pago no encontrado con ID: 99", exception.getMessage());
    }
}