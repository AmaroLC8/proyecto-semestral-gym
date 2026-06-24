package com.grupito.reservas_services.services;

import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.repository.ReservasRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservaService {
    private final ReservasRepository repo;
    public ReservaService(ReservasRepository repo) { this.repo = repo; }

    public List<Reservas> listar() { return repo.findAll(); }
    public Reservas obtenerPorId(Long id) { return repo.findById(id).orElse(null); }
    public Reservas guardar(Reservas reserva) { return repo.save(reserva); }
    public void eliminar(Long id) { repo.deleteById(id); }
}