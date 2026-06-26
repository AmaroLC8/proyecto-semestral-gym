package com.grupito.reservas_services.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.reservas_services.exception.BadRequestException;
import com.grupito.reservas_services.exception.ResourceNotFoundException;
import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.repository.ReservasRepository;

@Service
public class ReservaService {

    private final ReservasRepository repo;

    private static final List<String> ESTADOS_VALIDOS =
            Arrays.asList("PENDIENTE", "CONFIRMADA", "CANCELADA");

    private static final int MAX_RESERVAS_POR_USUARIO = 5;

    public ReservaService(ReservasRepository repo) {
        this.repo = repo;
    }

    public List<Reservas> listar() {
        return repo.findAll();
    }

    public Reservas obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
    }

    /**
     * Guarda una reserva aplicando las siguientes reglas de negocio:
     *
     * REGLA 1: El estado de la reserva debe ser un valor valido:
     *          PENDIENTE, CONFIRMADA o CANCELADA.
     *
     * REGLA 2: La fecha de la reserva no puede ser anterior a la fecha actual.
     *          Solo se permiten reservas para el dia de hoy o fechas futuras.
     *
     * REGLA 3: Un usuario no puede tener mas de 5 reservas en estado PENDIENTE
     *          al mismo tiempo (limite de reservas activas por usuario).
     */
    public Reservas guardar(Reservas reserva) {

        // REGLA 1: Estado valido
        if (reserva.getEstado() != null &&
                !ESTADOS_VALIDOS.contains(reserva.getEstado().toUpperCase())) {
            throw new BadRequestException(
                    "Estado invalido. Debe ser: " + ESTADOS_VALIDOS);
        }

        // REGLA 2: La fecha no puede ser en el pasado
        if (reserva.getFechaReserva() != null && reserva.getFechaReserva().before(new Date())) {
            throw new BadRequestException(
                    "La fecha de la reserva no puede ser anterior a la fecha actual.");
        }

        // REGLA 3: Maximo de reservas PENDIENTES por usuario
        if (reserva.getIdUsuario() != null) {
            long reservasPendientes = repo.findAll().stream()
                    .filter(r -> r.getIdUsuario().equals(reserva.getIdUsuario())
                            && "PENDIENTE".equals(r.getEstado()))
                    .count();
            if (reservasPendientes >= MAX_RESERVAS_POR_USUARIO) {
                throw new BadRequestException(
                        "El usuario con id " + reserva.getIdUsuario() +
                        " ya tiene " + MAX_RESERVAS_POR_USUARIO + " reservas pendientes. " +
                        "No se pueden crear mas hasta confirmar o cancelar las existentes.");
            }
        }

        return repo.save(reserva);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
