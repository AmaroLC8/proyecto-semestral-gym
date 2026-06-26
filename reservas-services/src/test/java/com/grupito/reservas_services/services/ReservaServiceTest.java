package com.grupito.reservas_services.services;

import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.repository.ReservasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock private ReservasRepository reservasRepository;
    @InjectMocks private ReservaService reservaService;
    private Reservas reservaFalsa;

    @BeforeEach
    void setUp() {
        reservaFalsa = new Reservas();
        reservaFalsa.setId(1L);
    }

    @Test
    @DisplayName("Buscar reserva exitosa por ID")
    void buscarPorId_Exito() {
        when(reservasRepository.findById(1L)).thenReturn(Optional.of(reservaFalsa));
        Reservas resultado = reservaService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Buscar reserva que no existe retorna null")
    void buscarPorId_Falla() {
        when(reservasRepository.findById(99L)).thenReturn(Optional.empty());
        assertNull(reservaService.obtenerPorId(99L));
    }

    @Test
    @DisplayName("Listar reservas")
    void listarReservas() {
        when(reservasRepository.findAll()).thenReturn(List.of(reservaFalsa));
        List<Reservas> resultados = reservaService.listar();
        assertEquals(1, resultados.size());
    }
}