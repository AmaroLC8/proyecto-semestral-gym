package com.grupito.usuarios_services.services;

import com.grupito.usuarios_services.model.Usuario;
import com.grupito.usuarios_services.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    public UsuarioService(UsuarioRepository repo) { this.repo = repo; }

    public Usuario guardar(Usuario u) { return repo.save(u); }
    public List<Usuario> listar() { return repo.findAll(); }
    public Usuario obtenerPorId(Long id) { return repo.findById(id).orElse(null); }
    public void eliminar(Long id) { repo.deleteById(id); }
    public boolean existePorId(Long id) { return repo.existsById(id); }
}