package com.grupito.soporte_services.controller;

import com.grupito.soporte_services.dto.SoporteDTO;
import com.grupito.soporte_services.model.Soporte;
import com.grupito.soporte_services.services.SoporteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/soporte")
public class SoporteController {
    private final SoporteService service;

    public SoporteController(SoporteService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Soporte> crear(@Valid @RequestBody SoporteDTO dto) {
        return ResponseEntity.ok(service.crearTicket(dto));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Soporte>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @PutMapping("/{id}/responder")
    public ResponseEntity<Soporte> responder(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
        Soporte actualizado = service.responderTicket(id, body.get("respuestaAdmin"));
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }
}