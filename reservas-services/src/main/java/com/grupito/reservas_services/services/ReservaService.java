package com.grupito.reservas_services.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.repository.ReservasRepository;

@Service
public class ReservaService {
    private final ReservasRepository reservasRepository;

    public ReservaService(ReservasRepository reservasRepository){
        this.reservasRepository = reservasRepository;
    }

    public Reservas guardar(Reservas cliente) {
        return reservasRepository.save(cliente);
    }

    public boolean existePorId(Long id) {
        return reservasRepository.existsById(id);
    }

    public List<Reservas> listar() {
        return reservasRepository.findAll();
    }

    public List<Reservas> buscarPorIdSocio(int idSocio) {
        return reservasRepository.findByIdSocio(idSocio);
    }

    public Reservas obtenerPorId(Long id) {
        return reservasRepository.findById(id).orElse(null);
    }

    public Reservas actualizar(Reservas reserva) {
        return reservasRepository.save(reserva);
    }

    public void eliminar(Long id) {
        reservasRepository.deleteById(id);
    }
}
