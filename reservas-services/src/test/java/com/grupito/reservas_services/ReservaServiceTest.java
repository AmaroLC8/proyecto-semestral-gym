package com.grupito.reservas_services;

import java.util.Arrays;
import java.util.Date;
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

import com.grupito.reservas_services.exception.ResourceNotFoundException;
import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.repository.ReservasRepository;
import com.grupito.reservas_services.services.ReservaService;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservasRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    public void testGuardarReserva_Exitoso() {
        Reservas reserva = Reservas.builder()
                .idUsuario(1L)
                .idProducto(100L)
                .fechaReserva(new Date())
                .estado("PENDIENTE")
                .build();

        when(reservaRepository.save(any(Reservas.class))).thenReturn(reserva);

        Reservas resultado = reservaService.guardar(reserva);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    public void testListarReservas() {
        Reservas r1 = Reservas.builder().id(1L).estado("PENDIENTE").build();
        Reservas r2 = Reservas.builder().id(2L).estado("CONFIRMADA").build();
        when(reservaRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Reservas> resultado = reservaService.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Reservas reserva = Reservas.builder().id(id).estado("PENDIENTE").build();
        when(reservaRepository.findById(id)).thenReturn(Optional.of(reserva));

        Reservas resultado = reservaService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> reservaService.obtenerPorId(id));
        assertTrue(exception.getMessage().contains("Reserva no encontrada"));
    }

    @Test
    public void testEliminarReserva() {
        Long id = 1L;
        doNothing().when(reservaRepository).deleteById(id);

        reservaService.eliminar(id);

        verify(reservaRepository, times(1)).deleteById(id);
    }
}