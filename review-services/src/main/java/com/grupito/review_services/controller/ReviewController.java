package com.grupito.review_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.grupito.review_services.assemblers.ReviewModelAssembler;
import com.grupito.review_services.dto.ReviewDTO;
import com.grupito.review_services.model.Review;
import com.grupito.review_services.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService service;
    private final ReviewModelAssembler assembler;

    public ReviewController(ReviewService service, ReviewModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // ── GET /reviews ──────────────────────────────────────────────────────────
    @GetMapping
    public CollectionModel<EntityModel<ReviewDTO>> listar() {
        logger.info("GET /reviews - Listando reviews");
        List<EntityModel<ReviewDTO>> reviews = service.listar().stream()
                .map(ReviewDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reviews,
                linkTo(methodOn(ReviewController.class).listar()).withSelfRel());
    }

    // ── GET /reviews/{id} ─────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public EntityModel<ReviewDTO> obtener(@PathVariable Long id) {
        logger.info("GET /reviews/{} - Obteniendo review", id);
        return assembler.toModel(ReviewDTO.fromModel(service.obtenerPorId(id)));
    }

    // ── POST /reviews ─────────────────────────────────────────────────────────
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<ReviewDTO> crear(@Valid @RequestBody ReviewDTO dto) {
        logger.info("POST /reviews - Creando review para productoId={}", dto.getIdProducto());
        Review r = service.guardar(dto.toModel());
        return assembler.toModel(ReviewDTO.fromModel(r));
    }

    // ── DELETE /reviews/{id} ──────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        logger.info("DELETE /reviews/{} - Eliminando review", id);
        service.eliminar(id);
    }
}