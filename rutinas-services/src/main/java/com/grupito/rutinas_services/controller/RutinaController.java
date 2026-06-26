package com.grupito.rutinas_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.grupito.rutinas_services.assemblers.RutinaModelAssembler;
import com.grupito.rutinas_services.dto.RutinaDTO;
import com.grupito.rutinas_services.model.Rutina;
import com.grupito.rutinas_services.services.RutinaServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rutinas")
public class RutinaController {

    private static final Logger logger = LoggerFactory.getLogger(RutinaController.class);

    private final RutinaServices service;
    private final RutinaModelAssembler assembler;

    public RutinaController(RutinaServices service, RutinaModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // ── GET /rutinas ──────────────────────────────────────────────────────────
    @GetMapping
    public CollectionModel<EntityModel<RutinaDTO>> listar() {
        logger.info("GET /rutinas - Listando rutinas");
        List<EntityModel<RutinaDTO>> rutinas = service.listar().stream()
                .map(RutinaDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(rutinas,
                linkTo(methodOn(RutinaController.class).listar()).withSelfRel());
    }

    // ── GET /rutinas/{id} ─────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public EntityModel<RutinaDTO> obtener(@PathVariable Long id) {
        logger.info("GET /rutinas/{} - Obteniendo rutina", id);
        return assembler.toModel(RutinaDTO.fromModel(service.obtenerPorId(id)));
    }

    // ── POST /rutinas ─────────────────────────────────────────────────────────
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<RutinaDTO> crear(@Valid @RequestBody RutinaDTO dto) {
        logger.info("POST /rutinas - Creando rutina: {}", dto.getNombre());
        Rutina r = service.guardar(dto.toModel());
        return assembler.toModel(RutinaDTO.fromModel(r));
    }

    // ── DELETE /rutinas/{id} ──────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        logger.info("DELETE /rutinas/{} - Eliminando rutina", id);
        service.eliminar(id);
    }
}