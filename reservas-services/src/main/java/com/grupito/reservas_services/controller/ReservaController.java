package com.grupito.reservas_services.controller;

import com.grupito.reservas_services.dto.ReservaDTO;
import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaService service;

    public ReservaController(ReservaService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> listar() {
        List<EntityModel<ReservaDTO>> reservas = service.listar().stream()
            .map(r -> EntityModel.of(ReservaDTO.fromModel(r),
                linkTo(methodOn(ReservaController.class).obtener(r.getId())).withSelfRel()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(reservas, linkTo(methodOn(ReservaController.class).listar()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReservaDTO>> obtener(@PathVariable Long id) {
        ReservaDTO dto = ReservaDTO.fromModel(service.obtenerPorId(id));
        return ResponseEntity.ok(EntityModel.of(dto, linkTo(methodOn(ReservaController.class).obtener(id)).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ReservaDTO>> crear(@Valid @RequestBody ReservaDTO dto) {
        Reservas r = service.guardar(dto.toModel());
        return ResponseEntity.ok(EntityModel.of(ReservaDTO.fromModel(r), linkTo(methodOn(ReservaController.class).obtener(r.getId())).withSelfRel()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}