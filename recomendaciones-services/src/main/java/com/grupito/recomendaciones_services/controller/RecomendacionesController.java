package com.grupito.recomendaciones_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.grupito.recomendaciones_services.assemblers.RecomendacionesModelAssembler;
import com.grupito.recomendaciones_services.dto.RecomendacionesDTO;
import com.grupito.recomendaciones_services.services.RecomendacionesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recomendaciones")
public class RecomendacionesController {

    private static final Logger logger = LoggerFactory.getLogger(RecomendacionesController.class);

    private final RecomendacionesService recomendacionesService;
    private final RecomendacionesModelAssembler assembler;

    public RecomendacionesController(RecomendacionesService recomendacionesService,
                                     RecomendacionesModelAssembler assembler) {
        this.recomendacionesService = recomendacionesService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<RecomendacionesDTO>> obtenerRecomendaciones() {
        logger.info("GET /recomendaciones - Listando recomendaciones");
        List<EntityModel<RecomendacionesDTO>> recomendaciones = recomendacionesService.obtenerRecomendaciones()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(recomendaciones,
                linkTo(methodOn(RecomendacionesController.class).obtenerRecomendaciones()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<RecomendacionesDTO> obtenerPorId(@PathVariable Long id) {
        logger.info("GET /recomendaciones/{} - Obteniendo recomendación", id);
        return assembler.toModel(recomendacionesService.obtenerPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<RecomendacionesDTO> crearRecomendacion(
            @Valid @RequestBody RecomendacionesDTO recomendacionesDto) {
        logger.info("POST /recomendaciones - Creando recomendación: {}", recomendacionesDto.getMensaje());
        RecomendacionesDTO guardada = recomendacionesService.crearRecomendacion(recomendacionesDto);
        return assembler.toModel(guardada);
    }
}

