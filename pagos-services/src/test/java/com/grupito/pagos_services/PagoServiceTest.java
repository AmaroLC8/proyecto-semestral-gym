package com.grupito.pagos_services;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.grupito.pagos_services.client.ReservaClient;
import com.grupito.pagos_services.client.UsuarioClient;
import com.grupito.pagos_services.exception.BadRequestException;
import com.grupito.pagos_services.exception.ResourceNotFoundException;
import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.repository.PagoRepository;
import com.grupito.pagos_services.services.PagoService;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private PagoService pagoService;

    // ============================================================
    // TESTS CRUD BASICOS
    // ============================================================

    @Test
    public void testProcesarYGuardarPago_CalculoCorrecto() {
        // GIVEN: Un pago de $10.000 con 20% de descuento.
        // 20% de 10000 = 2000. Subtotal = 8000. IVA (19%) = 1520. Total = 9520.
        Pago pagoEntrada = new Pago();
        pagoEntrada.setValorNeto(10000);
        pagoEntrada.setDescuento(20);
        pagoEntrada.setMedioPago("EFECTIVO");
        pagoEntrada.setIdCompra(1L);

        when(pagoRepository.save(any(Pago.class))).thenAnswer(i -> i.getArguments()[0]);

        Pago resultado = pagoService.procesarYGuardarPago(pagoEntrada);

        assertEquals(1520, resultado.getIva());
        assertEquals(9520, resultado.getTotalPagar());
        assertNotNull(resultado.getFecha());
        verify(pagoRepository, times(1)).save(pagoEntrada);
    }

    @Test
    public void testObtenerPago_Existente() {
        Long id = 1L;
        Pago pago = new Pago();
        pago.setId(id);
        pago.setValorNeto(5000);
        pago.setMedioPago("TRANSFERENCIA");
        when(pagoRepository.findById(id)).thenReturn(Optional.of(pago));

        Pago resultado = pagoService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(5000, resultado.getValorNeto());
    }

    @Test
    public void testObtenerPago_NoEncontrado() {
        Long idInexistente = 99L;
        when(pagoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> pagoService.obtenerPorId(idInexistente));
        assertEquals("Pago no encontrado con ID: 99", ex.getMessage());
    }

    @Test
    public void testEliminarPago() {
        Long id = 1L;
        doNothing().when(pagoRepository).deleteById(id);

        pagoService.eliminar(id);

        verify(pagoRepository, times(1)).deleteById(id);
    }

    // ============================================================
    // TESTS REGLAS DE NEGOCIO
    // ============================================================

    /**
     * REGLA 1: El descuento no puede superar el 50%.
     */
    @Test
    public void testRegla1_DescuentoSuperiorAl50_LanzaExcepcion() {
        Pago pago = new Pago();
        pago.setValorNeto(10000);
        pago.setDescuento(75); // 75% > 50%
        pago.setMedioPago("EFECTIVO");
        pago.setIdCompra(1L);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> pagoService.procesarYGuardarPago(pago));
        assertTrue(ex.getMessage().contains("descuento no puede superar"));
    }

    /**
     * REGLA 2: El valor neto no puede superar los 10.000.000.
     */
    @Test
    public void testRegla2_ValorNetoSuperaElMaximo_LanzaExcepcion() {
        Pago pago = new Pago();
        pago.setValorNeto(15_000_000);
        pago.setDescuento(0);
        pago.setMedioPago("TRANSFERENCIA");
        pago.setIdCompra(1L);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> pagoService.procesarYGuardarPago(pago));
        assertTrue(ex.getMessage().contains("valor neto supera el maximo permitido"));
    }

    /**
     * REGLA 3: El medio de pago debe ser valido.
     */
    @Test
    public void testRegla3_MedioPagoInvalido_LanzaExcepcion() {
        Pago pago = new Pago();
        pago.setValorNeto(10000);
        pago.setDescuento(10);
        pago.setMedioPago("CRIPTOMONEDA"); // No es un medio valido
        pago.setIdCompra(1L);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> pagoService.procesarYGuardarPago(pago));
        assertTrue(ex.getMessage().contains("Medio de pago invalido"));
    }
}