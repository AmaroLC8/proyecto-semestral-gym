package com.grupito.rutinas_services.services;

import com.grupito.rutinas_services.model.Rutina;
import com.grupito.rutinas_services.repository.RutinaRepository;
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
class RutinaServicesTest {

    @Mock private RutinaRepository rutinaRepository;
    @InjectMocks private RutinaServices rutinaServices;
    private Rutina rutinaFalsa;

    @BeforeEach
    void setUp() {
        rutinaFalsa = new Rutina();
        rutinaFalsa.setId(1L);
    }

    @Test
    @DisplayName("Buscar rutina exitosa")
    void buscarPorId_Exito() {
        when(rutinaRepository.findById(1L)).thenReturn(Optional.of(rutinaFalsa));
        Rutina resultado = rutinaServices.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Listar rutinas")
    void listarRutinas() {
        when(rutinaRepository.findAll()).thenReturn(List.of(rutinaFalsa));
        List<Rutina> resultados = rutinaServices.listar();
        assertEquals(1, resultados.size());
    }
}