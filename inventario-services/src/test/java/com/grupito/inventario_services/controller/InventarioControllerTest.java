package com.grupito.inventario_services.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupito.inventario_services.dto.InventarioDTO;
import com.grupito.inventario_services.model.Inventarios;
import com.grupito.inventario_services.service.InventarioService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void debeCrearInventario() throws Exception {

        InventarioDTO dto = new InventarioDTO(
                1L,
                "Mancuernas",
                "Set de mancuernas",
                10,
                true,
                "Pesas"
        );

        Inventarios inventario = new Inventarios(
                1L,
                "Mancuernas",
                "Set de mancuernas",
                10,
                true,
                "Pesas"
        );

        when(inventarioService.guardar(any(Inventarios.class)))
                .thenReturn(inventario);

        mockMvc.perform(post("/inventarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(inventarioService)
                .guardar(any(Inventarios.class));
    }

    @Test
    void debeListarInventarios() throws Exception {

        Inventarios inventario = new Inventarios(
                1L,
                "Mancuernas",
                "Set de mancuernas",
                10,
                true,
                "Pesas"
        );

        when(inventarioService.listar())
                .thenReturn(List.of(inventario));

        mockMvc.perform(get("/inventarios"))
                .andExpect(status().isOk());

        verify(inventarioService)
                .listar();
    }

    @Test
    void debeBuscarPorId() throws Exception {

        Inventarios inventario = new Inventarios(
                1L,
                "Mancuernas",
                "Set de mancuernas",
                10,
                true,
                "Pesas"
        );

        when(inventarioService.obtenerPorId(1L))
                .thenReturn(inventario);

        mockMvc.perform(get("/inventarios/1"))
                .andExpect(status().isOk());

        verify(inventarioService)
                .obtenerPorId(1L);
    }

    @Test
    void debeActualizarInventario() throws Exception {

        InventarioDTO dto = new InventarioDTO(
                1L,
                "Mancuernas Pro",
                "Actualizado",
                15,
                true,
                "Pesas"
        );

        Inventarios inventario = new Inventarios(
                1L,
                "Mancuernas Pro",
                "Actualizado",
                15,
                true,
                "Pesas"
        );

        when(inventarioService.existePorId(1L))
                .thenReturn(true);

        when(inventarioService.actualizar(any(Inventarios.class)))
                .thenReturn(inventario);

        mockMvc.perform(put("/inventarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(inventarioService)
                .actualizar(any(Inventarios.class));
    }

    @Test
    void debeEliminarInventario() throws Exception {

        when(inventarioService.existePorId(1L))
                .thenReturn(true);

        doNothing().when(inventarioService)
                .eliminar(1L);

        mockMvc.perform(delete("/inventarios/1"))
                .andExpect(status().isNoContent());

        verify(inventarioService)
                .eliminar(1L);
    }

    @Test
    void debeVerificarExistencia() throws Exception {

        when(inventarioService.existePorId(1L))
                .thenReturn(true);

        mockMvc.perform(get("/inventarios/1/exists"))
                .andExpect(status().isOk());

        verify(inventarioService)
                .existePorId(1L);
    }
}