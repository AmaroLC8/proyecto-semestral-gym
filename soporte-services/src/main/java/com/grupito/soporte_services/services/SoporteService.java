package com.grupito.soporte_services.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.soporte_services.dto.SoporteDTO;
import com.grupito.soporte_services.model.Soporte;
import com.grupito.soporte_services.repository.SoporteRepository;

@Service
public class SoporteService {

	private final SoporteRepository soporteRepository;

	public SoporteService(SoporteRepository soporteRepository) {
		this.soporteRepository = soporteRepository;
	}

	public Soporte crearTicket(SoporteDTO dto) {
		Soporte s = new Soporte();
		s.setUsuarioId(dto.getUsuarioId());
		s.setAsunto(dto.getAsunto());
		s.setDescripcion(dto.getDescripcion());
		s.setEstado("PENDIENTE");
		s.setFechaCreacion(LocalDateTime.now());
		return soporteRepository.save(s);
	}

	public List<Soporte> listarPorUsuario(Long usuarioId) {
		return soporteRepository.findByUsuarioId(usuarioId);
	}

	public List<Soporte> listarPorEstado(String estado) {
		return soporteRepository.findByEstado(estado);
	}

	public Soporte responderTicket(Long id, String respuesta) {
		return soporteRepository.findById(id).map(s -> {
			s.setRespuestaAdmin(respuesta);
			s.setEstado("RESUELTO");
			return soporteRepository.save(s);
		}).orElse(null);
	}
}

