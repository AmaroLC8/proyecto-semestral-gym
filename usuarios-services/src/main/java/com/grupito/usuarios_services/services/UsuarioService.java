package com.grupito.usuarios_services.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.usuarios_services.model.Usuario;
import com.grupito.usuarios_services.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario guardar(Usuario cliente) {
        return usuarioRepository.save(cliente);
    }

    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}


