package com.grupito.soporte_services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
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

import com.grupito.soporte_services.dto.SoporteDTO;
import com.grupito.soporte_services.exception.BadRequestException;
import com.grupito.soporte_services.exception.ResourceNotFoundException;
import com.grupito.soporte_services.model.Soporte;
import com.grupito.soporte_services.repository.SoporteRepository;
import com.grupito.soporte_services.services.SoporteService;

@ExtendWith(MockitoExtension.class)
public class SoporteServiceTest {

    @Mock
    private SoporteRepository soporteRepository;

    @InjectMocks
    private SoporteService soporteService;

    // ============================================================
    // TESTS CRUD BASICOS
    // ============================================================

    @Test
    public void testCrearTicket_Exitoso() {
        SoporteDTO dto = SoporteDTO.builder()
                .usuarioId(1L)
                .asunto("Problema con mi membresia")
                .descripcion("No puedo acceder a las clases avanzadas aunque tengo membresia premium activa")
                .build();

        Soporte guardado = Soporte.builder()
                .id(1L).usuarioId(1L).asunto(dto.getAsunto())
                .descripcion(dto.getDescripcion()).estado("PENDIENTE")
                .fechaCreacion(LocalDateTime.now())
                .build();

        when(soporteRepository.findByUsuarioId(1L)).thenReturn(Collections.emptyList());
        when(soporteRepository.save(any(Soporte.class))).thenReturn(guardado);

        Soporte resultado = soporteService.crearTicket(dto);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(soporteRepository, times(1)).save(any(Soporte.class));
    }

    @Test
    public void testListarTodos() {
        Soporte s1 = Soporte.builder().id(1L).usuarioId(1L).asunto("Problema A").estado("PENDIENTE").build();
        Soporte s2 = Soporte.builder().id(2L).usuarioId(2L).asunto("Problema B").estado("RESUELTO").build();
        when(soporteRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Soporte> resultado = soporteService.listarTodos();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Soporte soporte = Soporte.builder().id(id).usuarioId(1L).asunto("Mi problema").estado("PENDIENTE").build();
        when(soporteRepository.findById(id)).thenReturn(Optional.of(soporte));

        Soporte resultado = soporteService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(soporteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> soporteService.obtenerPorId(id));
    }

    @Test
    public void testResponderTicket() {
        Long id = 1L;
        Soporte soporte = Soporte.builder().id(id).usuarioId(1L).asunto("Problema").estado("PENDIENTE").build();
        when(soporteRepository.findById(id)).thenReturn(Optional.of(soporte));
        when(soporteRepository.save(any(Soporte.class))).thenReturn(soporte);

        Soporte resultado = soporteService.responderTicket(id, "Hemos solucionado el inconveniente");

        assertEquals("RESUELTO", resultado.getEstado());
        verify(soporteRepository, times(1)).save(soporte);
    }

    // ============================================================
    // TESTS REGLAS DE NEGOCIO
    // ============================================================

    /**
     * REGLA 1: Un usuario no puede tener mas de 3 tickets PENDIENTES al mismo tiempo.
     */
    @Test
    public void testRegla1_LimiteTicketsPendientes_LanzaExcepcion() {
        Long usuarioId = 1L;
        List<Soporte> ticketsPendientes = Arrays.asList(
                Soporte.builder().id(1L).usuarioId(usuarioId).asunto("Asunto 1").estado("PENDIENTE").build(),
                Soporte.builder().id(2L).usuarioId(usuarioId).asunto("Asunto 2").estado("PENDIENTE").build(),
                Soporte.builder().id(3L).usuarioId(usuarioId).asunto("Asunto 3").estado("PENDIENTE").build()
        );

        SoporteDTO dtoNuevo = SoporteDTO.builder()
                .usuarioId(usuarioId)
                .asunto("Asunto 4")
                .descripcion("Esta es mi descripcion detallada del nuevo problema que tengo con el servicio")
                .build();

        when(soporteRepository.findByUsuarioId(usuarioId)).thenReturn(ticketsPendientes);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> soporteService.crearTicket(dtoNuevo));
        assertTrue(ex.getMessage().contains("tickets pendientes"));
    }

    /**
     * REGLA 2: No se puede crear un ticket con el mismo asunto pendiente del mismo usuario.
     */
    @Test
    public void testRegla2_AsuntoDuplicado_LanzaExcepcion() {
        Long usuarioId = 1L;
        String asuntoDuplicado = "Problema con mi membresia";

        List<Soporte> ticketsExistentes = Collections.singletonList(
                Soporte.builder().id(1L).usuarioId(usuarioId).asunto(asuntoDuplicado).estado("PENDIENTE").build()
        );

        SoporteDTO dtoNuevo = SoporteDTO.builder()
                .usuarioId(usuarioId)
                .asunto(asuntoDuplicado)
                .descripcion("Sigo teniendo problemas con mi membresia premium desde hace dias")
                .build();

        when(soporteRepository.findByUsuarioId(usuarioId)).thenReturn(ticketsExistentes);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> soporteService.crearTicket(dtoNuevo));
        assertTrue(ex.getMessage().contains("ticket pendiente con el asunto"));
    }

    /**
     * REGLA 3: La descripcion debe tener al menos 20 caracteres.
     */
    @Test
    public void testRegla3_DescripcionMuyCorta_LanzaExcepcion() {
        Long usuarioId = 1L;
        SoporteDTO dto = SoporteDTO.builder()
                .usuarioId(usuarioId)
                .asunto("Problema con acceso")
                .descripcion("No funciona")  // Menos de 20 caracteres
                .build();

        when(soporteRepository.findByUsuarioId(usuarioId)).thenReturn(Collections.emptyList());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> soporteService.crearTicket(dto));
        assertTrue(ex.getMessage().contains("al menos"));
    }
}