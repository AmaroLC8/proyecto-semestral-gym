package com.grupito.reservas_services.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.reservas_services.exception.ResourceNotFoundException;
import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.repository.ReservasRepository;

@Service
public class ReservaService {
    private final ReservasRepository repo;
    public ReservaService(ReservasRepository repo) { this.repo = repo; }

    public List<Reservas> listar() { return repo.findAll(); }
    public Reservas obtenerPorId(Long id) { 
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id)); 
    }
    public Reservas guardar(Reservas reserva) {
        if (reserva.getEstado() != null && !reserva.getEstado().matches("PENDIENTE|CONFIRMADA|CANCELADA")) {
            throw new IllegalArgumentException("Estado inválido. Debe ser: PENDIENTE, CONFIRMADA o CANCELADA");
        }
        return repo.save(reserva);
    }
    public void eliminar(Long id) { repo.deleteById(id); }
}
