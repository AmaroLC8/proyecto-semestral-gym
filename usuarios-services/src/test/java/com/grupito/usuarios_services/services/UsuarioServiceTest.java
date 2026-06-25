package com.grupito.usuarios_services.services;

import com.grupito.usuarios_services.model.Usuario;
import com.grupito.usuarios_services.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(
                1L,
                "Amaro",
                "amaro@gym.com",
                "+56912345678",
                "Oro"
        );
    }

    @Test
    void deberiaGuardarUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        
        Usuario resultado = usuarioService.guardar(usuario);

        assertNotNull(resultado);
        assertEquals("Amaro", resultado.getNombre());
        assertEquals("amaro@gym.com", resultado.getCorreo());

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void deberiaListarUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> usuarios = usuarioService.listar();

        assertEquals(1, usuarios.size());
        assertEquals("Amaro", usuarios.get(0).getNombre());

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void deberiaBuscarUsuarioPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioNoExiste() {
        
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.obtenerPorId(99L);

        assertNull(resultado); 

        verify(usuarioRepository, times(1)).findById(99L);
    }

    @Test
    void deberiaActualizarUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario actualizado = usuarioService.actualizar(usuario);

        assertNotNull(actualizado);
        assertEquals("Amaro", actualizado.getNombre());

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void deberiaEliminarUsuario() {
        // Tu código usa deleteById
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.eliminar(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void deberiaVerificarExistenciaUsuario() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean existe = usuarioService.existePorId(1L);

        assertTrue(existe);

        verify(usuarioRepository, times(1)).existsById(1L);
    }

    @Test
    void deberiaRetornarFalseCuandoUsuarioNoExiste() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        boolean existe = usuarioService.existePorId(99L);

        assertFalse(existe);

        verify(usuarioRepository, times(1)).existsById(99L);
    }
}