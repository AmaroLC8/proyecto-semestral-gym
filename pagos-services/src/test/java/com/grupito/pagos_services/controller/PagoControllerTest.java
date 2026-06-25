package com.grupito.pagos_services.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupito.pagos_services.dto.PagoDTO;
import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.services.PagoService;
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

@WebMvcTest(PagoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PagoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private PagoService pagoService;
    @Autowired private ObjectMapper objectMapper;

    private Pago pagoFalso;
    private PagoDTO pagoDTO;

    @BeforeEach
    void setUp() {
        pagoFalso = new Pago();
        pagoFalso.setId(1L);
        pagoFalso.setMonto(15000.0);
        pagoFalso.setMetodoPago("Efectivo");

        pagoDTO = new PagoDTO();
        pagoDTO.setId(1L);
        pagoDTO.setMonto(15000.0);
        pagoDTO.setMetodo_pago("Efectivo");
    }

    @Test
    @DisplayName("Debe buscar pago por ID y retornar 200 OK")
    void debeBuscarPorId() throws Exception {
        when(pagoService.obtenerPorId(1L)).thenReturn(pagoFalso);
        mockMvc.perform(get("/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(15000.0)); // Cambiamos metodoPago por monto
    }

    @Test
    @DisplayName("Debe listar pagos y retornar 200 OK")
    void debeListarPagos() throws Exception {
        when(pagoService.listar()).thenReturn(List.of(pagoFalso));
        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].monto").value(15000.0));
    }

    @Test
    @DisplayName("Debe crear pago y retornar 200 OK")
    void debeCrearPago() throws Exception {
        when(pagoService.guardar(any(Pago.class))).thenReturn(pagoFalso);
        mockMvc.perform(post("/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoDTO)))
                .andExpect(status().isOk());
    }
}