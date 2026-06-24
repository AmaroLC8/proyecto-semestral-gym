package com.grupito.soporte_services.services;

import com.grupito.soporte_services.dto.SoporteDTO;
import com.grupito.soporte_services.model.Soporte;
import com.grupito.soporte_services.repository.SoporteRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SoporteService {
    private final SoporteRepository repo;
    public SoporteService(SoporteRepository repo) { this.repo = repo; }

    public Soporte crearTicket(SoporteDTO dto) {
        Soporte s = dto.toModel();
        s.setEstado("PENDIENTE");
        s.setFechaCreacion(LocalDateTime.now());
        return repo.save(s);
    }

    public List<Soporte> listarPorUsuario(Long usuarioId) { return repo.findByUsuarioId(usuarioId); }

    public Soporte responderTicket(Long id, String respuesta) {
        return repo.findById(id).map(s -> {
            s.setRespuestaAdmin(respuesta);
            s.setEstado("RESUELTO");
            return repo.save(s);
        }).orElse(null);
    }
}