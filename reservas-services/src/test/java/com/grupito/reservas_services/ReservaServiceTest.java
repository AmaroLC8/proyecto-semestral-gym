package com.grupito.reservas_services;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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

import com.grupito.reservas_services.exception.BadRequestException;
import com.grupito.reservas_services.exception.ResourceNotFoundException;
import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.repository.ReservasRepository;
import com.grupito.reservas_services.services.ReservaService;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservasRepository reservasRepository;

    @InjectMocks
    private ReservaService reservaService;

    // ============================================================
    // TESTS CRUD BASICOS
    // ============================================================

    @Test
    public void testGuardarReserva_Exitoso() {
        // Fecha futura (10 dias desde ahora)
        Date fechaFutura = new Date(System.currentTimeMillis() + 10L * 24 * 60 * 60 * 1000);
        Reservas reserva = Reservas.builder()
                .idUsuario(1L).idProducto(2L)
                .fechaReserva(fechaFutura).estado("PENDIENTE")
                .build();

        when(reservasRepository.findAll()).thenReturn(Collections.emptyList());
        when(reservasRepository.save(any(Reservas.class))).thenReturn(reserva);

        Reservas resultado = reservaService.guardar(reserva);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(reservasRepository, times(1)).save(reserva);
    }

    @Test
    public void testListarReservas() {
        Reservas r1 = Reservas.builder().id(1L).idUsuario(1L).idProducto(1L).estado("PENDIENTE").build();
        Reservas r2 = Reservas.builder().id(2L).idUsuario(2L).idProducto(2L).estado("CONFIRMADA").build();
        when(reservasRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Reservas> resultado = reservaService.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Reservas reserva = Reservas.builder().id(id).idUsuario(1L).idProducto(2L).estado("CONFIRMADA").build();
        when(reservasRepository.findById(id)).thenReturn(Optional.of(reserva));

        Reservas resultado = reservaService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("CONFIRMADA", resultado.getEstado());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(reservasRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reservaService.obtenerPorId(id));
    }

    @Test
    public void testEliminarReserva() {
        Long id = 1L;
        doNothing().when(reservasRepository).deleteById(id);

        reservaService.eliminar(id);

        verify(reservasRepository, times(1)).deleteById(id);
    }

    // ============================================================
    // TESTS REGLAS DE NEGOCIO
    // ============================================================

    /**
     * REGLA 1: El estado de la reserva debe ser valido (PENDIENTE, CONFIRMADA, CANCELADA).
     */
    @Test
    public void testRegla1_EstadoInvalido_LanzaExcepcion() {
        Date fechaFutura = new Date(System.currentTimeMillis() + 5L * 24 * 60 * 60 * 1000);
        Reservas reserva = Reservas.builder()
                .idUsuario(1L).idProducto(2L)
                .fechaReserva(fechaFutura).estado("EN_PROCESO")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> reservaService.guardar(reserva));
        assertTrue(ex.getMessage().contains("Estado invalido"));
    }

    /**
     * REGLA 2: La fecha de la reserva no puede ser en el pasado.
     */
    @Test
    public void testRegla2_FechaEnElPasado_LanzaExcepcion() {
        Date fechaPasada = new Date(System.currentTimeMillis() - 24L * 60 * 60 * 1000);
        Reservas reserva = Reservas.builder()
                .idUsuario(1L).idProducto(2L)
                .fechaReserva(fechaPasada).estado("PENDIENTE")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> reservaService.guardar(reserva));
        assertTrue(ex.getMessage().contains("fecha de la reserva no puede ser anterior"));
    }

    /**
     * REGLA 3: Un usuario no puede tener mas de 5 reservas PENDIENTES al mismo tiempo.
     */
    @Test
    public void testRegla3_LimiteReservasPendientesExcedido_LanzaExcepcion() {
        Long usuarioId = 1L;
        Date fechaFutura = new Date(System.currentTimeMillis() + 5L * 24 * 60 * 60 * 1000);

        // Simular 5 reservas pendientes del mismo usuario
        List<Reservas> reservasExistentes = Arrays.asList(
                Reservas.builder().id(1L).idUsuario(usuarioId).idProducto(1L).estado("PENDIENTE").build(),
                Reservas.builder().id(2L).idUsuario(usuarioId).idProducto(2L).estado("PENDIENTE").build(),
                Reservas.builder().id(3L).idUsuario(usuarioId).idProducto(3L).estado("PENDIENTE").build(),
                Reservas.builder().id(4L).idUsuario(usuarioId).idProducto(4L).estado("PENDIENTE").build(),
                Reservas.builder().id(5L).idUsuario(usuarioId).idProducto(5L).estado("PENDIENTE").build()
        );

        Reservas nuevaReserva = Reservas.builder()
                .idUsuario(usuarioId).idProducto(6L)
                .fechaReserva(fechaFutura).estado("PENDIENTE")
                .build();

        when(reservasRepository.findAll()).thenReturn(reservasExistentes);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> reservaService.guardar(nuevaReserva));
        assertTrue(ex.getMessage().contains("reservas pendientes"));
    }
}