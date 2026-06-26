package com.grupito.recomendaciones_services;

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

import com.grupito.recomendaciones_services.dto.RecomendacionesDTO;
import com.grupito.recomendaciones_services.exception.BadRequestException;
import com.grupito.recomendaciones_services.exception.ResourceNotFoundException;
import com.grupito.recomendaciones_services.model.Recomendaciones;
import com.grupito.recomendaciones_services.repository.RecomendacionesRepository;
import com.grupito.recomendaciones_services.services.RecomendacionesService;

@ExtendWith(MockitoExtension.class)
public class RecomendacionesServiceTest {

    @Mock
    private RecomendacionesRepository recomendacionesRepository;

    @InjectMocks
    private RecomendacionesService recomendacionesService;

    // ============================================================
    // TESTS CRUD BASICOS
    // ============================================================

    @Test
    public void testCrearRecomendacion_Exitoso() {
        RecomendacionesDTO dto = RecomendacionesDTO.builder()
                .mensaje("Deberias aumentar tu ingesta de proteinas post entrenamiento")
                .idSocio(1)
                .build();

        Recomendaciones guardada = Recomendaciones.builder()
                .id(1L)
                .mensaje(dto.getMensaje())
                .idSocio(dto.getIdSocio())
                .build();

        when(recomendacionesRepository.findAll()).thenReturn(Collections.emptyList());
        when(recomendacionesRepository.save(any(Recomendaciones.class))).thenReturn(guardada);

        RecomendacionesDTO resultado = recomendacionesService.crearRecomendacion(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(recomendacionesRepository, times(1)).save(any(Recomendaciones.class));
    }

    @Test
    public void testObtenerRecomendaciones() {
        Recomendaciones r1 = Recomendaciones.builder().id(1L).mensaje("Incrementa tus repeticiones de sentadillas").idSocio(1).build();
        Recomendaciones r2 = Recomendaciones.builder().id(2L).mensaje("Mantener hidratacion constante durante el ejercicio").idSocio(2).build();
        when(recomendacionesRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<RecomendacionesDTO> resultado = recomendacionesService.obtenerRecomendaciones();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Recomendaciones rec = Recomendaciones.builder().id(id).mensaje("Mejora tu postura al levantar peso").idSocio(1).build();
        when(recomendacionesRepository.findById(id)).thenReturn(Optional.of(rec));

        RecomendacionesDTO resultado = recomendacionesService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(recomendacionesRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recomendacionesService.obtenerPorId(id));
    }

    @Test
    public void testEliminarRecomendacion() {
        Long id = 1L;
        doNothing().when(recomendacionesRepository).deleteById(id);

        recomendacionesService.eliminar(id);

        verify(recomendacionesRepository, times(1)).deleteById(id);
    }

    // ============================================================
    // TESTS REGLAS DE NEGOCIO
    // ============================================================

    /**
     * REGLA 1: El mensaje debe tener al menos 10 caracteres.
     */
    @Test
    public void testRegla1_MensajeMuyCorto_LanzaExcepcion() {
        RecomendacionesDTO dto = RecomendacionesDTO.builder()
                .mensaje("Corto")
                .idSocio(1)
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> recomendacionesService.crearRecomendacion(dto));
        assertTrue(ex.getMessage().contains("al menos"));
    }

    /**
     * REGLA 2: El mensaje no puede contener palabras prohibidas (spam/publicidad).
     */
    @Test
    public void testRegla2_ContenidoSpam_LanzaExcepcion() {
        RecomendacionesDTO dto = RecomendacionesDTO.builder()
                .mensaje("Gran oferta en suplementos, compra ahora y aprovecha")
                .idSocio(1)
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> recomendacionesService.crearRecomendacion(dto));
        assertTrue(ex.getMessage().contains("contenido no permitido"));
    }

    /**
     * REGLA 3: Un socio no puede tener mas de 10 recomendaciones activas.
     */
    @Test
    public void testRegla3_LimiteRecomendaciones_LanzaExcepcion() {
        Integer socioId = 1;
        // Simular 10 recomendaciones existentes del mismo socio
        List<Recomendaciones> recsExistentes = Arrays.asList(
                Recomendaciones.builder().id(1L).mensaje("Recomendacion 1").idSocio(socioId).build(),
                Recomendaciones.builder().id(2L).mensaje("Recomendacion 2").idSocio(socioId).build(),
                Recomendaciones.builder().id(3L).mensaje("Recomendacion 3").idSocio(socioId).build(),
                Recomendaciones.builder().id(4L).mensaje("Recomendacion 4").idSocio(socioId).build(),
                Recomendaciones.builder().id(5L).mensaje("Recomendacion 5").idSocio(socioId).build(),
                Recomendaciones.builder().id(6L).mensaje("Recomendacion 6").idSocio(socioId).build(),
                Recomendaciones.builder().id(7L).mensaje("Recomendacion 7").idSocio(socioId).build(),
                Recomendaciones.builder().id(8L).mensaje("Recomendacion 8").idSocio(socioId).build(),
                Recomendaciones.builder().id(9L).mensaje("Recomendacion 9").idSocio(socioId).build(),
                Recomendaciones.builder().id(10L).mensaje("Recomendacion 10").idSocio(socioId).build()
        );

        RecomendacionesDTO dtoNuevo = RecomendacionesDTO.builder()
                .mensaje("Intenta mejorar tu flexibilidad con estiramientos diarios")
                .idSocio(socioId)
                .build();

        when(recomendacionesRepository.findAll()).thenReturn(recsExistentes);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> recomendacionesService.crearRecomendacion(dtoNuevo));
        assertTrue(ex.getMessage().contains("recomendaciones"));
    }
}