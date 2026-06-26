package com.grupito.soporte_services;

import com.grupito.soporte_services.dto.SoporteDTO;
import com.grupito.soporte_services.exception.ResourceNotFoundException;
import com.grupito.soporte_services.model.Soporte;
import com.grupito.soporte_services.repository.SoporteRepository;
import com.grupito.soporte_services.services.SoporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SoporteServiceTest {

    @Mock
    private SoporteRepository soporteRepository;

    @InjectMocks
    private SoporteService soporteService;

    @Test
    public void testCrearTicket_Exitoso() {
        SoporteDTO dto = SoporteDTO.builder()
                .usuarioId(1L)
                .asunto("Problema con máquina")
                .descripcion("La máquina no funciona")
                .build();

        when(soporteRepository.save(any(Soporte.class))).thenAnswer(i -> i.getArguments()[0]);

        Soporte resultado = soporteService.crearTicket(dto);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        assertNotNull(resultado.getFechaCreacion());
        verify(soporteRepository, times(1)).save(any(Soporte.class));
    }

    @Test
    public void testListarTodos() {
        Soporte s1 = Soporte.builder().id(1L).estado("PENDIENTE").build();
        Soporte s2 = Soporte.builder().id(2L).estado("RESUELTO").build();
        when(soporteRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Soporte> resultado = soporteService.listarTodos();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Soporte soporte = Soporte.builder().id(id).estado("PENDIENTE").build();
        when(soporteRepository.findById(id)).thenReturn(Optional.of(soporte));

        Soporte resultado = soporteService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(soporteRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> soporteService.obtenerPorId(id));
        assertTrue(exception.getMessage().contains("Ticket de soporte no encontrado"));
    }

    @Test
    public void testResponderTicket_Exitoso() {
        Long id = 1L;
        Soporte soporte = Soporte.builder().id(id).estado("PENDIENTE").build();
        when(soporteRepository.findById(id)).thenReturn(Optional.of(soporte));
        when(soporteRepository.save(any(Soporte.class))).thenAnswer(i -> i.getArguments()[0]);

        Soporte resultado = soporteService.responderTicket(id, "Solución aplicada");

        assertNotNull(resultado);
        assertEquals("RESUELTO", resultado.getEstado());
        assertEquals("Solución aplicada", resultado.getRespuestaAdmin());
    }
}