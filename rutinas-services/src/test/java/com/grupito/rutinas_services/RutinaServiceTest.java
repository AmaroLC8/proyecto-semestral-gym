package com.grupito.rutinas_services;

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

import com.grupito.rutinas_services.exception.BadRequestException;
import com.grupito.rutinas_services.exception.ResourceNotFoundException;
import com.grupito.rutinas_services.model.Rutina;
import com.grupito.rutinas_services.repository.RutinaRepository;
import com.grupito.rutinas_services.services.RutinaServices;

@ExtendWith(MockitoExtension.class)
public class RutinaServiceTest {

    @Mock
    private RutinaRepository rutinaRepository;

    @InjectMocks
    private RutinaServices rutinaServices;

    // ============================================================
    // TESTS CRUD BASICOS
    // ============================================================

    @Test
    public void testGuardarRutina_Exitoso() {
        Rutina rutina = Rutina.builder()
                .nombre("Cardio Total")
                .descripcion("Rutina de cardio intensiva")
                .duracionMinutos(45)
                .nivelDificultad("INTERMEDIO")
                .build();

        when(rutinaRepository.findAll()).thenReturn(Collections.emptyList());
        when(rutinaRepository.save(any(Rutina.class))).thenReturn(rutina);

        Rutina resultado = rutinaServices.guardar(rutina);

        assertNotNull(resultado);
        assertEquals("Cardio Total", resultado.getNombre());
        verify(rutinaRepository, times(1)).save(rutina);
    }

    @Test
    public void testListarRutinas() {
        Rutina r1 = Rutina.builder().id(1L).nombre("Fuerza Total").duracionMinutos(60).nivelDificultad("AVANZADO").build();
        Rutina r2 = Rutina.builder().id(2L).nombre("Yoga Basico").duracionMinutos(30).nivelDificultad("PRINCIPIANTE").build();
        when(rutinaRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Rutina> resultado = rutinaServices.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Rutina rutina = Rutina.builder().id(id).nombre("Fuerza Total").duracionMinutos(60).nivelDificultad("AVANZADO").build();
        when(rutinaRepository.findById(id)).thenReturn(Optional.of(rutina));

        Rutina resultado = rutinaServices.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Fuerza Total", resultado.getNombre());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(rutinaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> rutinaServices.obtenerPorId(id));
    }

    @Test
    public void testEliminarRutina() {
        Long id = 1L;
        doNothing().when(rutinaRepository).deleteById(id);

        rutinaServices.eliminar(id);

        verify(rutinaRepository, times(1)).deleteById(id);
    }

    // ============================================================
    // TESTS REGLAS DE NEGOCIO
    // ============================================================

    /**
     * REGLA 1: La duracion debe estar entre 10 y 300 minutos.
     * Verificamos que una duracion menor a 10 lance BadRequestException.
     */
    @Test
    public void testRegla1_DuracionMenorAlMinimo_LanzaExcepcion() {
        Rutina rutina = Rutina.builder()
                .nombre("Rutina Corta")
                .duracionMinutos(5)
                .nivelDificultad("PRINCIPIANTE")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> rutinaServices.guardar(rutina));
        assertTrue(ex.getMessage().contains("duracion de la rutina debe estar entre"));
    }

    /**
     * REGLA 1 (borde superior): Duracion mayor a 300 minutos.
     */
    @Test
    public void testRegla1_DuracionSuperiorAlMaximo_LanzaExcepcion() {
        Rutina rutina = Rutina.builder()
                .nombre("Rutina Maratonica")
                .duracionMinutos(400)
                .nivelDificultad("AVANZADO")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> rutinaServices.guardar(rutina));
        assertTrue(ex.getMessage().contains("duracion de la rutina debe estar entre"));
    }

    /**
     * REGLA 2: El nivel de dificultad debe ser valido (PRINCIPIANTE, INTERMEDIO, AVANZADO).
     */
    @Test
    public void testRegla2_NivelDificultadInvalido_LanzaExcepcion() {
        Rutina rutina = Rutina.builder()
                .nombre("Rutina Elite")
                .duracionMinutos(60)
                .nivelDificultad("EXPERTO")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> rutinaServices.guardar(rutina));
        assertTrue(ex.getMessage().contains("Nivel de dificultad invalido"));
    }

    /**
     * REGLA 3: El nombre de la rutina debe ser unico (sin importar mayusculas).
     */
    @Test
    public void testRegla3_NombreDuplicado_LanzaExcepcion() {
        Rutina existente = Rutina.builder().id(1L).nombre("Cardio Total").duracionMinutos(45).nivelDificultad("INTERMEDIO").build();
        Rutina nueva = Rutina.builder().nombre("cardio total").duracionMinutos(30).nivelDificultad("PRINCIPIANTE").build();

        when(rutinaRepository.findAll()).thenReturn(Collections.singletonList(existente));

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> rutinaServices.guardar(nueva));
        assertTrue(ex.getMessage().contains("Ya existe una rutina con el nombre"));
    }
}