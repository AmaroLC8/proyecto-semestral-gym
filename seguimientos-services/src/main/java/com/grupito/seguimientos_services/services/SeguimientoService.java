package com.grupito.seguimientos_services.services;

import com.grupito.seguimientos_services.exception.ResourceNotFoundException;
import com.grupito.seguimientos_services.model.Seguimiento;
import com.grupito.seguimientos_services.repository.SeguimientoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeguimientoService {
    private final SeguimientoRepository repo;
    public SeguimientoService(SeguimientoRepository repo) { this.repo = repo; }

    public List<Seguimiento> listar() { return repo.findAll(); }
    public Seguimiento guardar(Seguimiento seguimiento) { return repo.save(seguimiento); }
    public void eliminar(Long id) { repo.deleteById(id); }
    public boolean existePorId(Long id) { return repo.existsById(id); }
    public Seguimiento obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seguimiento no encontrado con id: " + id));
    }
}