package com.grupito.recomendaciones_services;

import java.util.Arrays;
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
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.grupito.recomendaciones_services.dto.RecomendacionesDTO;
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

    @Test
    public void testObtenerRecomendaciones() {
        Recomendaciones r1 = Recomendaciones.builder().id(1L).mensaje("Toma más proteína").idSocio(1).build();
        Recomendaciones r2 = Recomendaciones.builder().id(2L).mensaje("Haz más cardio").idSocio(2).build();
        when(recomendacionesRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<RecomendacionesDTO> resultado = recomendacionesService.obtenerRecomendaciones();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Recomendaciones r = Recomendaciones.builder().id(id).mensaje("Toma más proteína").idSocio(1).build();
        when(recomendacionesRepository.findById(id)).thenReturn(Optional.of(r));

        RecomendacionesDTO resultado = recomendacionesService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Toma más proteína", resultado.getMensaje());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(recomendacionesRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> recomendacionesService.obtenerPorId(id));
        assertTrue(exception.getMessage().contains("Recomendación no encontrada"));
    }

    @Test
    public void testCrearRecomendacion_Exitoso() {
        RecomendacionesDTO dto = RecomendacionesDTO.builder()
                .mensaje("Toma más proteína")
                .idSocio(1)
                .build();

        when(recomendacionesRepository.save(any(Recomendaciones.class))).thenAnswer(i -> i.getArguments()[0]);

        RecomendacionesDTO resultado = recomendacionesService.crearRecomendacion(dto);

        assertNotNull(resultado);
        assertEquals("Toma más proteína", resultado.getMensaje());
    }
}