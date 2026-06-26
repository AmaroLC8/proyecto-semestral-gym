package com.grupito.usuarios_services.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupito.usuarios_services.dto.UsuarioDTO;
import com.grupito.usuarios_services.model.Usuario;
import com.grupito.usuarios_services.services.UsuarioService;

import org.junit.jupiter.api.DisplayName;
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

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debe crear usuario correctamente")
    void debeCrearUsuario() throws Exception {
        UsuarioDTO dto = new UsuarioDTO(
                1L,
                "Amaro",
                "amaro@gym.com",
                "+56912345678",
                "Oro"
        );

        Usuario usuario = new Usuario(
                1L,
                "Amaro",
                "amaro@gym.com",
                "+56912345678",
                "Oro"
        );

        
        when(usuarioService.guardar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()); 

        verify(usuarioService, times(1)).guardar(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe listar usuarios")
    void debeListarUsuarios() throws Exception {
        Usuario usuario = new Usuario(
                1L,
                "Amaro",
                "amaro@gym.com",
                "+56912345678",
                "Oro"
        );

        when(usuarioService.listar()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).listar();
    }

    @Test
    @DisplayName("Debe buscar usuario por ID")
    void debeBuscarUsuarioPorId() throws Exception {
        Usuario usuario = new Usuario(
                1L,
                "Amaro",
                "amaro@gym.com",
                "+56912345678",
                "Oro"
        );

        
        when(usuarioService.obtenerPorId(1L)).thenReturn(usuario);

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).obtenerPorId(1L);
    }

    @Test
    @DisplayName("Debe eliminar usuario")
    void debeEliminarUsuario() throws Exception {
        
        when(usuarioService.existePorId(1L)).thenReturn(true);
        doNothing().when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).eliminar(1L);
    }

    @Test
    @DisplayName("Debe verificar existencia")
    void debeVerificarExistencia() throws Exception {
        
        when(usuarioService.existePorId(1L)).thenReturn(true);

        mockMvc.perform(get("/usuarios/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true")); 

        verify(usuarioService, times(1)).existePorId(1L);
    }
}