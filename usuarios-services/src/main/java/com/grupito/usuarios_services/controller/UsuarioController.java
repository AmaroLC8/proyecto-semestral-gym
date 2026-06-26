package com.grupito.usuarios_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.grupito.usuarios_services.assemblers.UsuarioModelAssembler;
import com.grupito.usuarios_services.dto.UsuarioDTO;
import com.grupito.usuarios_services.model.Usuario;
import com.grupito.usuarios_services.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService service;
    private final UsuarioModelAssembler assembler;

    public UsuarioController(UsuarioService service, UsuarioModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<UsuarioDTO>> listar() {
        logger.info("GET /usuarios - Listando usuarios");
        List<EntityModel<UsuarioDTO>> usuarios = service.listar().stream()
                .map(UsuarioDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<UsuarioDTO> obtener(@PathVariable Long id) {
        logger.info("GET /usuarios/{} - Obteniendo usuario", id);
        return assembler.toModel(UsuarioDTO.fromModel(service.obtenerPorId(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UsuarioDTO> crear(@Valid @RequestBody UsuarioDTO dto) {
        logger.info("POST /usuarios - Creando usuario: {}", dto.getNombre());
        Usuario u = service.guardar(dto.toModel());
        return assembler.toModel(UsuarioDTO.fromModel(u));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        logger.info("DELETE /usuarios/{} - Eliminando usuario", id);
        service.eliminar(id);
    }
}