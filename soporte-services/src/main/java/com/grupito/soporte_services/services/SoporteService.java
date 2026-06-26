package com.grupito.soporte_services.services;

import com.grupito.soporte_services.dto.SoporteDTO;
import com.grupito.soporte_services.exception.BadRequestException;
import com.grupito.soporte_services.exception.ResourceNotFoundException;
import com.grupito.soporte_services.model.Soporte;
import com.grupito.soporte_services.repository.SoporteRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SoporteService {

    private final SoporteRepository repo;

    private static final int MAX_TICKETS_PENDIENTES_POR_USUARIO = 3;
    private static final int LONGITUD_MINIMA_RESPUESTA = 20;

    public SoporteService(SoporteRepository repo) {
        this.repo = repo;
    }

    /**
     * Crea un ticket de soporte aplicando las siguientes reglas de negocio:
     *
     * REGLA 1: Un usuario no puede tener mas de 3 tickets en estado PENDIENTE
     *          al mismo tiempo. Esto evita el abuso del sistema de soporte y
     *          garantiza que se resuelvan los tickets existentes antes de crear nuevos.
     *
     * REGLA 2: El asunto del ticket no puede ser identico a un ticket PENDIENTE
     *          existente del mismo usuario. Esto previene la creacion de tickets
     *          duplicados sobre el mismo problema.
     *
     * REGLA 3: La descripcion del ticket debe tener al menos 20 caracteres.
     *          Descripciones demasiado cortas no aportan suficiente contexto
     *          para que el equipo de soporte pueda ayudar de forma efectiva.
     */
    public Soporte crearTicket(SoporteDTO dto) {

        // REGLA 1: Maximo 3 tickets PENDIENTES por usuario
        long ticketsPendientes = repo.findByUsuarioId(dto.getUsuarioId()).stream()
                .filter(t -> "PENDIENTE".equals(t.getEstado()))
                .count();
        if (ticketsPendientes >= MAX_TICKETS_PENDIENTES_POR_USUARIO) {
            throw new BadRequestException(
                    "El usuario con id " + dto.getUsuarioId() +
                    " ya tiene " + MAX_TICKETS_PENDIENTES_POR_USUARIO +
                    " tickets pendientes. Espere a que sean resueltos antes de crear uno nuevo.");
        }

        // REGLA 2: No crear tickets duplicados (mismo asunto pendiente)
        boolean asuntoDuplicado = repo.findByUsuarioId(dto.getUsuarioId()).stream()
                .filter(t -> "PENDIENTE".equals(t.getEstado()))
                .anyMatch(t -> t.getAsunto().equalsIgnoreCase(dto.getAsunto()));
        if (asuntoDuplicado) {
            throw new BadRequestException(
                    "Ya existe un ticket pendiente con el asunto '" + dto.getAsunto() +
                    "' para este usuario. Por favor, espere a que sea resuelto.");
        }

        // REGLA 3: Descripcion con minimo de caracteres
        if (dto.getDescripcion() == null || dto.getDescripcion().trim().length() < LONGITUD_MINIMA_RESPUESTA) {
            throw new BadRequestException(
                    "La descripcion debe tener al menos " + LONGITUD_MINIMA_RESPUESTA + " caracteres.");
        }

        Soporte s = dto.toModel();
        s.setEstado("PENDIENTE");
        s.setFechaCreacion(LocalDateTime.now());
        return repo.save(s);
    }

    public List<Soporte> listarTodos() {
        return repo.findAll();
    }

    public Soporte obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket de soporte no encontrado con id: " + id));
    }

    public List<Soporte> listarPorUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public Soporte responderTicket(Long id, String respuesta) {
        Soporte s = obtenerPorId(id);
        s.setRespuestaAdmin(respuesta);
        s.setEstado("RESUELTO");
        return repo.save(s);
    }
}