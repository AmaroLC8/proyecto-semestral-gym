package com.grupito.rutinas_services.controller;

import com.grupito.rutinas_services.dto.RutinaDTO;
import com.grupito.rutinas_services.model.Rutina;
import com.grupito.rutinas_services.services.RutinaServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rutinas")
public class RutinaController {
    private final RutinaServices service;

    public RutinaController(RutinaServices service) { this.service = service; }

    @PostMapping
    public ResponseEntity<RutinaDTO> crear(@Valid @RequestBody RutinaDTO dto) {
        Rutina r = service.guardar(dto.toModel());
        return ResponseEntity.ok(RutinaDTO.fromModel(r));
    }

    @GetMapping
    public ResponseEntity<List<RutinaDTO>> listar() {
        List<RutinaDTO> dtos = service.listar().stream()
                .map(RutinaDTO::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}