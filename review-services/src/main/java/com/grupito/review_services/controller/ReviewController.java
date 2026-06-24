package com.grupito.review_services.controller;

import com.grupito.review_services.dto.ReviewDTO;
import com.grupito.review_services.model.Review;

import com.grupito.review_services.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReviewDTO>>> listar() {
        List<EntityModel<ReviewDTO>> reviews = service.listar().stream()
            .map(r -> EntityModel.of(ReviewDTO.fromModel(r),
                linkTo(methodOn(ReviewController.class).obtener(r.getId())).withSelfRel()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(reviews, 
            linkTo(methodOn(ReviewController.class).listar()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReviewDTO>> obtener(@PathVariable Long id) {
        ReviewDTO dto = ReviewDTO.fromModel(service.obtenerPorId(id));
        return ResponseEntity.ok(EntityModel.of(dto,
            linkTo(methodOn(ReviewController.class).obtener(id)).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ReviewDTO>> crear(@Valid @RequestBody ReviewDTO dto) {
        Review r = service.guardar(dto.toModel());
        return ResponseEntity.ok(EntityModel.of(ReviewDTO.fromModel(r)));
    }
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build(); 
    }
}