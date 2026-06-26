package com.grupito.seguimientos_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.grupito.seguimientos_services.assemblers.SeguimientoModelAssembler;
import com.grupito.seguimientos_services.dto.SeguimientoDTO;
import com.grupito.seguimientos_services.model.Seguimiento;
import com.grupito.seguimientos_services.services.SeguimientoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/seguimientos")
public class SeguimientoController {

    private static final Logger logger = LoggerFactory.getLogger(SeguimientoController.class);

    private final SeguimientoService service;
    private final SeguimientoModelAssembler assembler;

    public SeguimientoController(SeguimientoService service, SeguimientoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<SeguimientoDTO>> listar() {
        logger.info("GET /seguimientos - Listando seguimientos");
        List<EntityModel<SeguimientoDTO>> seguimientos = service.listar().stream()
                .map(SeguimientoDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(seguimientos,
                linkTo(methodOn(SeguimientoController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<SeguimientoDTO> obtener(@PathVariable Long id) {
        logger.info("GET /seguimientos/{} - Obteniendo seguimiento", id);
        return assembler.toModel(SeguimientoDTO.fromModel(service.obtenerPorId(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<SeguimientoDTO> crear(@Valid @RequestBody SeguimientoDTO dto) {
        logger.info("POST /seguimientos - Registrando seguimiento para socioId={}", dto.getIdSocio());
        Seguimiento s = service.guardar(dto.toModel());
        return assembler.toModel(SeguimientoDTO.fromModel(s));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        logger.info("DELETE /seguimientos/{} - Eliminando seguimiento", id);
        service.eliminar(id);
    }
}
