package com.grupito.usuarios_services.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.usuarios_services.exception.BadRequestException;
import com.grupito.usuarios_services.exception.ResourceNotFoundException;
import com.grupito.usuarios_services.model.Usuario;
import com.grupito.usuarios_services.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    private static final List<String> MEMBRESIAS_VALIDAS =
            Arrays.asList("BASICA", "ESTANDAR", "PREMIUM", "VIP");

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    /**
     * Guarda un usuario aplicando las siguientes reglas de negocio:
     *
     * REGLA 1: El correo electronico debe ser unico en el sistema.
     *          No se pueden registrar dos usuarios con el mismo correo.
     *
     * REGLA 2: El tipo de membresia debe ser uno de los valores permitidos:
     *          BASICA, ESTANDAR, PREMIUM o VIP.
     *
     * REGLA 3: El nombre del usuario no puede tener menos de 2 palabras
     *          (debe ingresar nombre y apellido como minimo).
     */
    public Usuario guardar(Usuario usuario) {

        // REGLA 1: Correo unico
        if (usuario.getCorreo() != null && repo.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new BadRequestException("El correo ya esta registrado: " + usuario.getCorreo());
        }

        // REGLA 2: Tipo de membresia valido
        if (usuario.getTipoMembresia() != null &&
                !MEMBRESIAS_VALIDAS.contains(usuario.getTipoMembresia().toUpperCase())) {
            throw new BadRequestException("Tipo de membresia invalido. Valores permitidos: " + MEMBRESIAS_VALIDAS);
        }

        // REGLA 3: El nombre debe contener al menos nombre y apellido (2 palabras)
        if (usuario.getNombre() == null || usuario.getNombre().trim().split("\\s+").length < 2) {
            throw new BadRequestException("El nombre debe contener al menos nombre y apellido.");
        }

        return repo.save(usuario);
    }

    public Usuario obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return repo.existsById(id);
    }
}
