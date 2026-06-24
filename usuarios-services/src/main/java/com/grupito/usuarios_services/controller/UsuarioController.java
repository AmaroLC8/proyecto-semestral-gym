package com.grupito.usuarios_services.controller;

import com.grupito.usuarios_services.dto.UsuarioDTO;
import com.grupito.usuarios_services.model.Usuario;
import com.grupito.usuarios_services.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioDTO dto) {
        Usuario u = service.guardar(dto.toModel());
        return ResponseEntity.ok(UsuarioDTO.fromModel(u));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<UsuarioDTO> dtos = service.listar().stream()
                .map(UsuarioDTO::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable Long id) {
        Usuario u = service.obtenerPorId(id);
        return u != null ? ResponseEntity.ok(UsuarioDTO.fromModel(u)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}