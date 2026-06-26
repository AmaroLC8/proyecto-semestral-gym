package com.grupito.usuarios_services;

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

import com.grupito.usuarios_services.exception.ResourceNotFoundException;
import com.grupito.usuarios_services.model.Usuario;
import com.grupito.usuarios_services.repository.UsuarioRepository;
import com.grupito.usuarios_services.services.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    public void testGuardarUsuario_Exitoso() {
        // GIVEN
        Usuario usuario = Usuario.builder()
                .nombre("Juan Pérez")
                .correo("juan@email.com")
                .telefono("123456789")
                .tipoMembresia("Premium")
                .build();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // WHEN
        Usuario resultado = usuarioService.guardar(usuario);

        // THEN
        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombre());
        assertEquals("juan@email.com", resultado.getCorreo());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testListarUsuarios() {
        // GIVEN
        Usuario u1 = Usuario.builder().id(1L).nombre("Juan").correo("juan@email.com").build();
        Usuario u2 = Usuario.builder().id(2L).nombre("María").correo("maria@email.com").build();
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        // WHEN
        List<Usuario> resultado = usuarioService.listar();

        // THEN
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerPorId_Existente() {
        // GIVEN
        Long id = 1L;
        Usuario usuario = Usuario.builder().id(id).nombre("Juan").correo("juan@email.com").build();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // WHEN
        Usuario resultado = usuarioService.obtenerPorId(id);

        // THEN
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        // GIVEN
        Long id = 99L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> usuarioService.obtenerPorId(id));
        assertTrue(exception.getMessage().contains("Usuario no encontrado"));
    }

    @Test
    public void testEliminarUsuario() {
        // GIVEN
        Long id = 1L;
        doNothing().when(usuarioRepository).deleteById(id);

        // WHEN
        usuarioService.eliminar(id);

        // THEN
        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testExistePorId() {
        // GIVEN
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(true);

        // WHEN
        boolean existe = usuarioService.existePorId(id);

        // THEN
        assertTrue(existe);
    }
}