package com.grupito.reservas_services.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupito.reservas_services.dto.ReservaDTO;
import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.services.ReservaService;
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

@WebMvcTest(ReservaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReservaControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private ReservaService reservaService;
    @Autowired private ObjectMapper objectMapper;

    private Reservas reservaFalsa;
    private ReservaDTO reservaDTO;

    @BeforeEach
    void setUp() {
        reservaFalsa = new Reservas();
        reservaFalsa.setId(1L);

        reservaDTO = new ReservaDTO();
        reservaDTO.setId(1L);
    }

    @Test
    @DisplayName("Debe buscar reserva por ID y retornar 200 OK")
    void debeBuscarPorId() throws Exception {
        when(reservaService.obtenerPorId(1L)).thenReturn(reservaFalsa);
        mockMvc.perform(get("/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe listar reservas y retornar 200 OK")
    void debeListarReservas() throws Exception {
        when(reservaService.listar()).thenReturn(List.of(reservaFalsa));
        mockMvc.perform(get("/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("Debe crear reserva y retornar 200 OK")
    void debeCrearReserva() throws Exception {
        when(reservaService.guardar(any(Reservas.class))).thenReturn(reservaFalsa);
        mockMvc.perform(post("/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaDTO)))
                .andExpect(status().isOk());
    }
}