package com.grupito.seguimientos_services;

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

    @Test
    public void testGuardarSeguimiento_Exitoso() {
        Seguimiento seguimiento = Seguimiento.builder()
                .idSocio(1)
                .peso(75.5)
                .porcentajeGrasa(20.0)
                .fechaRegistro(new java.util.Date())
                .build();

        when(seguimientoRepository.save(any(Seguimiento.class))).thenReturn(seguimiento);

        Seguimiento resultado = seguimientoService.guardar(seguimiento);

        assertNotNull(resultado);
        assertEquals(75.5, resultado.getPeso());
        verify(seguimientoRepository, times(1)).save(seguimiento);
    }

    @Test
    public void testListarSeguimientos() {
        Seguimiento s1 = Seguimiento.builder().id(1L).peso(70.0).build();
        Seguimiento s2 = Seguimiento.builder().id(2L).peso(80.0).build();
        when(seguimientoRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Seguimiento> resultado = seguimientoService.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Seguimiento seguimiento = Seguimiento.builder().id(id).peso(70.0).build();
        when(seguimientoRepository.findById(id)).thenReturn(Optional.of(seguimiento));

        Seguimiento resultado = seguimientoService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(70.0, resultado.getPeso());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(seguimientoRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> seguimientoService.obtenerPorId(id));
        assertTrue(exception.getMessage().contains("Seguimiento no encontrado"));
    }

    @Test
    public void testEliminarSeguimiento() {
        Long id = 1L;
        doNothing().when(seguimientoRepository).deleteById(id);

        seguimientoService.eliminar(id);

        verify(seguimientoRepository, times(1)).deleteById(id);
    }
}