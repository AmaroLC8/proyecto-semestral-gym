package com.grupito.usuarios_services.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.usuarios_services.exception.ResourceNotFoundException;
import com.grupito.usuarios_services.model.Usuario;
import com.grupito.usuarios_services.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    public UsuarioService(UsuarioRepository repo) { this.repo = repo; }

    public List<Usuario> listar() { return repo.findAll(); }
    
    public Usuario guardar(Usuario usuario) {
        if (usuario.getCorreo() != null && repo.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado: " + usuario.getCorreo());
        }
        return repo.save(usuario);
    }
    
    public Usuario obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }
    public void eliminar(Long id) { repo.deleteById(id); }
    public boolean existePorId(Long id) { return repo.existsById(id); }
}
