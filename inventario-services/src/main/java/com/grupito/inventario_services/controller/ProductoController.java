package com.grupito.inventario_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.inventario_services.assemblers.ProductoModelAssembler;
import com.grupito.inventario_services.dto.ProductoDTO;
import com.grupito.inventario_services.model.Producto;
import com.grupito.inventario_services.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventario")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final ProductoService service;
    private final ProductoModelAssembler assembler;

    public ProductoController(ProductoService service, ProductoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // ── GET /productos ────────────────────────────────────────────────────────
    @GetMapping
    public CollectionModel<EntityModel<ProductoDTO>> listar() {
        logger.info("GET /productos - Listando productos");
        List<EntityModel<ProductoDTO>> productos = service.listar().stream()
                .map(ProductoDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listar()).withSelfRel());
    }

    // ── GET /productos/{id} ───────────────────────────────────────────────────
    @GetMapping("/{id}")
    public EntityModel<ProductoDTO> obtener(@PathVariable Long id) {
        logger.info("GET /productos/{} - Obteniendo producto", id);
        return assembler.toModel(ProductoDTO.fromModel(service.obtenerPorId(id)));
    }

    // ── POST /productos ───────────────────────────────────────────────────────
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        logger.info("POST /productos - Creando producto: {}", dto.getNombre());
        Producto p = service.guardar(dto.toModel());
        return assembler.toModel(ProductoDTO.fromModel(p));
    }

    // ── DELETE /productos/{id} ────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        logger.info("DELETE /productos/{} - Eliminando producto", id);
        service.eliminar(id);
    }
}
