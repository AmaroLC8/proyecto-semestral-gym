package com.grupito.soporte_services.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.grupito.soporte_services.assemblers.SoporteModelAssembler;
import com.grupito.soporte_services.dto.SoporteDTO;
import com.grupito.soporte_services.model.Soporte;
import com.grupito.soporte_services.services.SoporteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/soporte")
public class SoporteController {

    private static final Logger logger = LoggerFactory.getLogger(SoporteController.class);

    private final SoporteService service;
    private final SoporteModelAssembler assembler;

    public SoporteController(SoporteService service, SoporteModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // ── GET /soporte ──────────────────────────────────────────────────────────
    @GetMapping
    public CollectionModel<EntityModel<SoporteDTO>> listar() {
        logger.info("GET /soporte - Listando todos los tickets de soporte");
        List<EntityModel<SoporteDTO>> tickets = service.listarTodos().stream()
                .map(SoporteDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tickets,
                linkTo(methodOn(SoporteController.class).listar()).withSelfRel());
    }

    // ── GET /soporte/{id} ─────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public EntityModel<SoporteDTO> obtenerPorId(@PathVariable Long id) {
        logger.info("GET /soporte/{} - Obteniendo ticket de soporte", id);
        Soporte soporte = service.obtenerPorId(id);
        return assembler.toModel(SoporteDTO.fromModel(soporte));
    }

    // ── GET /soporte/usuario/{usuarioId} ──────────────────────────────────────
    @GetMapping("/usuario/{usuarioId}")
    public CollectionModel<EntityModel<SoporteDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        logger.info("GET /soporte/usuario/{} - Listando tickets por usuario", usuarioId);
        List<EntityModel<SoporteDTO>> tickets = service.listarPorUsuario(usuarioId).stream()
                .map(SoporteDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tickets,
                linkTo(methodOn(SoporteController.class).listarPorUsuario(usuarioId)).withSelfRel(),
                linkTo(methodOn(SoporteController.class).listar()).withRel("soporte-collection"));
    }

    // ── POST /soporte ─────────────────────────────────────────────────────────
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<SoporteDTO> crear(@Valid @RequestBody SoporteDTO dto) {
        logger.info("POST /soporte - Creando nuevo ticket de soporte para usuarioId={}", dto.getUsuarioId());
        Soporte creado = service.crearTicket(dto);
        return assembler.toModel(SoporteDTO.fromModel(creado));
    }

    // ── PUT /soporte/{id}/responder ────────────────────────────────────────────
    @PutMapping("/{id}/responder")
    public EntityModel<SoporteDTO> responder(@PathVariable Long id,
                                             @RequestBody Map<String, String> body) {
        logger.info("PUT /soporte/{}/responder - Respondiendo ticket de soporte", id);
        Soporte actualizado = service.responderTicket(id, body.get("respuestaAdmin"));
        return assembler.toModel(SoporteDTO.fromModel(actualizado));
    }
}