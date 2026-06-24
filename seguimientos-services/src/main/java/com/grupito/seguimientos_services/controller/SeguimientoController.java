package com.grupito.seguimientos_services.controller;

import com.grupito.seguimientos_services.dto.SeguimientoDTO;
import com.grupito.seguimientos_services.model.Seguimiento;
import com.grupito.seguimientos_services.services.SeguimientoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seguimientos")
public class SeguimientoController {
    private final SeguimientoService service;

    public SeguimientoController(SeguimientoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<SeguimientoDTO>> listar() {
        List<SeguimientoDTO> dtos = service.listar().stream()
            .map(SeguimientoDTO::fromModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<SeguimientoDTO> crear(@Valid @RequestBody SeguimientoDTO dto) {
        Seguimiento s = service.guardar(dto.toModel());
        return ResponseEntity.ok(SeguimientoDTO.fromModel(s));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}