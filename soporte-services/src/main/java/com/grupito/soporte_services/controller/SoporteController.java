package com.grupito.soporte_services.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.soporte_services.dto.SoporteDTO;
import com.grupito.soporte_services.model.Soporte;
import com.grupito.soporte_services.services.SoporteService;

@RestController
@RequestMapping("/soporte")
public class SoporteController {

	private static final Logger logger = LoggerFactory.getLogger(SoporteController.class);

	private final SoporteService soporteService;

	public SoporteController(SoporteService soporteService) {
		this.soporteService = soporteService;
	}

	@PostMapping
	public ResponseEntity<Soporte> crearTicket(@RequestBody SoporteDTO dto) {
		if (dto.getUsuarioId() == null || dto.getAsunto() == null || dto.getDescripcion() == null) {
			return ResponseEntity.badRequest().build();
		}
		Soporte creado = soporteService.crearTicket(dto);
		logger.info("Ticket creado id={} usuarioId={}", creado.getId(), creado.getUsuarioId());
		return ResponseEntity.ok(creado);
	}

	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Soporte>> listarPorUsuario(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(soporteService.listarPorUsuario(usuarioId));
	}

	@PutMapping("/{id}/responder")
	public ResponseEntity<Soporte> responderTicket(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
		String respuesta = body.get("respuestaAdmin");
		if (respuesta == null) return ResponseEntity.badRequest().build();
		Soporte actualizado = soporteService.responderTicket(id, respuesta);
		if (actualizado == null) return ResponseEntity.notFound().build();
		logger.info("Ticket id={} resuelto", id);
		return ResponseEntity.ok(actualizado);
	}
}

