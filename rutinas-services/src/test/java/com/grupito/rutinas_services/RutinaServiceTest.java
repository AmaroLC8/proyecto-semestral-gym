package com.grupito.rutinas_services;

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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.grupito.rutinas_services.exception.ResourceNotFoundException;
import com.grupito.rutinas_services.model.Rutina;
import com.grupito.rutinas_services.repository.RutinaRepository;
import com.grupito.rutinas_services.services.RutinaServices;

@ExtendWith(MockitoExtension.class)
public class RutinaServiceTest {

    @Mock
    private RutinaRepository rutinaRepository;

    @InjectMocks
    private RutinaServices rutinaService;

    @Test
    public void testGuardarRutina_Exitoso() {
        Rutina rutina = Rutina.builder()
                .nombre("Rutina de fuerza")
                .descripcion("Ejercicios de fuerza")
                .duracionMinutos(45)
                .nivelDificultad("Intermedio")
                .build();

        when(rutinaRepository.save(any(Rutina.class))).thenReturn(rutina);

        Rutina resultado = rutinaService.guardar(rutina);

        assertNotNull(resultado);
        assertEquals("Rutina de fuerza", resultado.getNombre());
        verify(rutinaRepository, times(1)).save(rutina);
    }

    @Test
    public void testListarRutinas() {
        Rutina r1 = Rutina.builder().id(1L).nombre("Cardio").build();
        Rutina r2 = Rutina.builder().id(2L).nombre("Yoga").build();
        when(rutinaRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Rutina> resultado = rutinaService.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Rutina rutina = Rutina.builder().id(id).nombre("Cardio").build();
        when(rutinaRepository.findById(id)).thenReturn(Optional.of(rutina));

        Rutina resultado = rutinaService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Cardio", resultado.getNombre());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(rutinaRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> rutinaService.obtenerPorId(id));
        assertTrue(exception.getMessage().contains("Rutina no encontrada"));
    }

    @Test
    public void testEliminarRutina() {
        Long id = 1L;
        doNothing().when(rutinaRepository).deleteById(id);

        rutinaService.eliminar(id);

        verify(rutinaRepository, times(1)).deleteById(id);
    }
}