package com.grupito.rutinas_services.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupito.rutinas_services.dto.RutinaDTO;
import com.grupito.rutinas_services.model.Rutina;
import com.grupito.rutinas_services.services.RutinaServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RutinaController.class)
@AutoConfigureMockMvc(addFilters = false)
class RutinaControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private RutinaServices rutinaServices;
    @Autowired private ObjectMapper objectMapper;

    private Rutina rutinaFalsa;
    private RutinaDTO rutinaDTO;

    @BeforeEach
    void setUp() {
        rutinaFalsa = new Rutina();
        rutinaFalsa.setId(1L);

        rutinaDTO = new RutinaDTO();
        rutinaDTO.setId(1L);
    }

    @Test
    @DisplayName("Debe buscar rutina por ID")
    void debeBuscarPorId() throws Exception {
        when(rutinaServices.obtenerPorId(1L)).thenReturn(rutinaFalsa);
        mockMvc.perform(get("/rutinas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe listar rutinas")
    void debeListarRutinas() throws Exception {
        when(rutinaServices.listar()).thenReturn(List.of(rutinaFalsa));
        mockMvc.perform(get("/rutinas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
    @Test
    @DisplayName("Debe crear rutina y retornar 200 OK")
    void debeCrearRutina() throws Exception {
        when(rutinaServices.guardar(any(Rutina.class))).thenReturn(rutinaFalsa);
        
        mockMvc.perform(post("/rutinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rutinaDTO)))
                .andExpect(status().isOk());
    }
}