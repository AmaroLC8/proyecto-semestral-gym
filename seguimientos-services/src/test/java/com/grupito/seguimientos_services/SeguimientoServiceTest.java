package com.grupito.seguimientos_services;

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

import com.grupito.seguimientos_services.exception.BadRequestException;
import com.grupito.seguimientos_services.exception.ResourceNotFoundException;
import com.grupito.seguimientos_services.model.Seguimiento;
import com.grupito.seguimientos_services.repository.SeguimientoRepository;
import com.grupito.seguimientos_services.services.SeguimientoService;

@ExtendWith(MockitoExtension.class)
public class SeguimientoServiceTest {

    @Mock
    private SeguimientoRepository seguimientoRepository;

    @InjectMocks
    private SeguimientoService seguimientoService;

    // ============================================================
    // TESTS CRUD BASICOS
    // ============================================================

    @Test
    public void testGuardarSeguimiento_Exitoso() {
        Seguimiento seg = Seguimiento.builder()
                .idSocio(1).peso(75.5).porcentajeGrasa(18.0).fechaRegistro(new Date())
                .build();

        when(seguimientoRepository.findAll()).thenReturn(Collections.emptyList());
        when(seguimientoRepository.save(any(Seguimiento.class))).thenReturn(seg);

        Seguimiento resultado = seguimientoService.guardar(seg);

        assertNotNull(resultado);
        assertEquals(75.5, resultado.getPeso());
        verify(seguimientoRepository, times(1)).save(seg);
    }

    @Test
    public void testListarSeguimientos() {
        Seguimiento s1 = Seguimiento.builder().id(1L).idSocio(1).peso(70.0).porcentajeGrasa(15.0).build();
        Seguimiento s2 = Seguimiento.builder().id(2L).idSocio(2).peso(80.0).porcentajeGrasa(20.0).build();
        when(seguimientoRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Seguimiento> resultado = seguimientoService.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Seguimiento seg = Seguimiento.builder().id(id).idSocio(1).peso(75.0).porcentajeGrasa(18.0).build();
        when(seguimientoRepository.findById(id)).thenReturn(Optional.of(seg));

        Seguimiento resultado = seguimientoService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(75.0, resultado.getPeso());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(seguimientoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> seguimientoService.obtenerPorId(id));
    }

    @Test
    public void testEliminarSeguimiento() {
        Long id = 1L;
        doNothing().when(seguimientoRepository).deleteById(id);

        seguimientoService.eliminar(id);

        verify(seguimientoRepository, times(1)).deleteById(id);
    }

    // ============================================================
    // TESTS REGLAS DE NEGOCIO
    // ============================================================

    /**
     * REGLA 1: El peso debe estar entre 20 y 300 kg.
     * Probamos con un peso menor al minimo.
     */
    @Test
    public void testRegla1_PesoMenorAlMinimo_LanzaExcepcion() {
        Seguimiento seg = Seguimiento.builder().idSocio(1).peso(10.0).porcentajeGrasa(18.0).build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> seguimientoService.guardar(seg));
        assertTrue(ex.getMessage().contains("El peso debe estar entre"));
    }

    /**
     * REGLA 1 (borde superior): Peso mayor a 300 kg.
     */
    @Test
    public void testRegla1_PesoMayorAlMaximo_LanzaExcepcion() {
        Seguimiento seg = Seguimiento.builder().idSocio(1).peso(350.0).porcentajeGrasa(18.0).build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> seguimientoService.guardar(seg));
        assertTrue(ex.getMessage().contains("El peso debe estar entre"));
    }

    /**
     * REGLA 2: El porcentaje de grasa no puede superar el 60%.
     */
    @Test
    public void testRegla2_GrasaMayorAlMaximo_LanzaExcepcion() {
        Seguimiento seg = Seguimiento.builder().idSocio(1).peso(80.0).porcentajeGrasa(65.0).build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> seguimientoService.guardar(seg));
        assertTrue(ex.getMessage().contains("porcentaje de grasa corporal no puede superar"));
    }

    /**
     * REGLA 3: Un socio no puede registrar seguimiento si tiene uno en los ultimos 3 dias.
     */
    @Test
    public void testRegla3_RegistroReciente_LanzaExcepcion() {
        int socioId = 1;
        // Seguimiento registrado hace 1 dia (dentro del periodo de cooldown)
        Date hace1Dia = new Date(System.currentTimeMillis() - 1L * 24 * 60 * 60 * 1000);
        Seguimiento reciente = Seguimiento.builder()
                .id(1L).idSocio(socioId).peso(70.0).porcentajeGrasa(18.0).fechaRegistro(hace1Dia).build();

        Seguimiento nuevo = Seguimiento.builder()
                .idSocio(socioId).peso(71.0).porcentajeGrasa(17.5).build();

        when(seguimientoRepository.findAll()).thenReturn(Collections.singletonList(reciente));

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> seguimientoService.guardar(nuevo));
        assertTrue(ex.getMessage().contains("seguimiento registrado en los ultimos"));
    }
}