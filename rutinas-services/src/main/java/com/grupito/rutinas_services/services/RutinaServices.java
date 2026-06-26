package com.grupito.rutinas_services.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.rutinas_services.exception.ResourceNotFoundException;
import com.grupito.rutinas_services.model.Rutina;
import com.grupito.rutinas_services.repository.RutinaRepository;

@Service
public class RutinaServices {
    private final RutinaRepository repo;
    public RutinaServices(RutinaRepository repo) { this.repo = repo; }

    public Rutina guardar(Rutina r) {
        if (r.getDuracionMinutos() <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 minutos");
        }
        return repo.save(r);
    }
    public List<Rutina> listar() { return repo.findAll(); }
    public void eliminar(Long id) { repo.deleteById(id); }
    public boolean existePorId(Long id) { return repo.existsById(id); }
    public Rutina obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rutina no encontrada con id: " + id));
    }
}
