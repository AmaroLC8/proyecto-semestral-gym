package com.grupito.inventario_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.inventario_services.dto.InventarioDTO;
import com.grupito.inventario_services.model.Inventarios;
import com.grupito.inventario_services.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/inventarios")
@Tag(name = "Inventario", description = "Gestión de inventario del gimnasio")
public class InventarioController {

    private static final Logger logger = LoggerFactory.getLogger(InventarioController.class);
    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo elemento de inventario")
    public ResponseEntity<InventarioDTO> crearInventario(@RequestBody InventarioDTO inventarioDto) {
        logger.info("Solicitud para crear inventario: {}", inventarioDto.getNombre());
        Inventarios nuevo = inventarioService.guardar(inventarioDto.toModel());
        return ResponseEntity.ok(InventarioDTO.fromModel(nuevo));
    }

    @GetMapping
    @Operation(summary = "Listar todos los elementos de inventario")
    public ResponseEntity<List<InventarioDTO>> listarInventarios() {
        List<Inventarios> inventarios = inventarioService.listar();
        List<InventarioDTO> dtos = inventarios.stream().map(InventarioDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un elemento de inventario por ID")
    public ResponseEntity<InventarioDTO> obtenerInventario(@PathVariable Long id) {
        Inventarios inventario = inventarioService.obtenerPorId(id);
        if (inventario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(InventarioDTO.fromModel(inventario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un elemento de inventario")
    public ResponseEntity<InventarioDTO> actualizarInventario(@PathVariable Long id,
            @RequestBody InventarioDTO inventarioDto) {
        if (!inventarioService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        inventarioDto.setId(id);
        Inventarios actualizado = inventarioService.actualizar(inventarioDto.toModel());
        return ResponseEntity.ok(InventarioDTO.fromModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un elemento de inventario")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long id) {
        if (!inventarioService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar si existe un elemento de inventario")
    public ResponseEntity<Boolean> existeInventario(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.existePorId(id));
    }
}
