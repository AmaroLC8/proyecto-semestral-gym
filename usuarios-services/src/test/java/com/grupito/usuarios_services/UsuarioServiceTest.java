package com.grupito.usuarios_services;

import java.util.Arrays;
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

import com.grupito.usuarios_services.exception.BadRequestException;
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

    // ============================================================
    // TESTS CRUD BASICOS
    // ============================================================

    @Test
    public void testGuardarUsuario_Exitoso() {
        Usuario usuario = Usuario.builder()
                .nombre("Juan Perez")
                .correo("juan@email.com")
                .telefono("123456789")
                .tipoMembresia("PREMIUM")
                .build();

        when(usuarioRepository.findByCorreo("juan@email.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.guardar(usuario);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testListarUsuarios() {
        Usuario u1 = Usuario.builder().id(1L).nombre("Juan Perez").correo("juan@email.com").tipoMembresia("BASICA").build();
        Usuario u2 = Usuario.builder().id(2L).nombre("Maria Lopez").correo("maria@email.com").tipoMembresia("PREMIUM").build();
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Usuario> resultado = usuarioService.listar();

        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Usuario usuario = Usuario.builder().id(id).nombre("Juan Perez").correo("juan@email.com").build();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.obtenerPorId(id));
    }

    @Test
    public void testEliminarUsuario() {
        Long id = 1L;
        doNothing().when(usuarioRepository).deleteById(id);

        usuarioService.eliminar(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testExistePorId() {
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(true);

        assertTrue(usuarioService.existePorId(id));
    }

    // ============================================================
    // TESTS REGLAS DE NEGOCIO
    // ============================================================

    /**
     * REGLA 1: El correo debe ser unico.
     * Verificamos que al intentar registrar un correo ya existente, se lance BadRequestException.
     */
    @Test
    public void testRegla1_CorreoDuplicado_LanzaExcepcion() {
        Usuario existente = Usuario.builder()
                .id(1L).nombre("Juan Perez").correo("juan@email.com").tipoMembresia("BASICA").build();
        Usuario nuevo = Usuario.builder()
                .nombre("Otro Nombre").correo("juan@email.com").tipoMembresia("PREMIUM").build();

        when(usuarioRepository.findByCorreo("juan@email.com")).thenReturn(Optional.of(existente));

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> usuarioService.guardar(nuevo));
        assertTrue(ex.getMessage().contains("correo ya esta registrado"));
    }

    /**
     * REGLA 2: El tipo de membresia debe ser valido (BASICA, ESTANDAR, PREMIUM, VIP).
     * Verificamos que una membresia invalida lance BadRequestException.
     */
    @Test
    public void testRegla2_MembresiaInvalida_LanzaExcepcion() {
        Usuario usuario = Usuario.builder()
                .nombre("Juan Perez").correo("juan2@email.com").tipoMembresia("GOLD").build();

        when(usuarioRepository.findByCorreo("juan2@email.com")).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> usuarioService.guardar(usuario));
        assertTrue(ex.getMessage().contains("Tipo de membresia invalido"));
    }

    /**
     * REGLA 3: El nombre debe contener al menos 2 palabras (nombre y apellido).
     * Verificamos que un nombre de una sola palabra lance BadRequestException.
     */
    @Test
    public void testRegla3_NombreSinApellido_LanzaExcepcion() {
        Usuario usuario = Usuario.builder()
                .nombre("Juan").correo("solo@email.com").tipoMembresia("BASICA").build();

        when(usuarioRepository.findByCorreo("solo@email.com")).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> usuarioService.guardar(usuario));
        assertTrue(ex.getMessage().contains("nombre y apellido"));
    }
}