package com.grupito.reservas_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.grupito.reservas_services.assemblers.ReservaModelAssembler;
import com.grupito.reservas_services.dto.ReservaDTO;
import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.services.ReservaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);

    private final ReservaService service;
    private final ReservaModelAssembler assembler;

    public ReservaController(ReservaService service, ReservaModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<ReservaDTO>> listar() {
        logger.info("GET /reservas - Listando reservas");
        List<EntityModel<ReservaDTO>> reservas = service.listar().stream()
                .map(ReservaDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ReservaDTO> obtener(@PathVariable Long id) {
        logger.info("GET /reservas/{} - Obteniendo reserva", id);
        return assembler.toModel(ReservaDTO.fromModel(service.obtenerPorId(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<ReservaDTO> crear(@Valid @RequestBody ReservaDTO dto) {
        logger.info("POST /reservas - Creando reserva para usuarioId={}", dto.getIdUsuario());
        Reservas r = service.guardar(dto.toModel());
        return assembler.toModel(ReservaDTO.fromModel(r));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        logger.info("DELETE /reservas/{} - Eliminando reserva", id);
        service.eliminar(id);
    }
}
