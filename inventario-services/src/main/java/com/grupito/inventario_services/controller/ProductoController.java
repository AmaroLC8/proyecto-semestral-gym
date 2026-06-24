package com.grupito.inventario_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.inventario_services.dto.ProductoDTO;
import com.grupito.inventario_services.model.Producto;
import com.grupito.inventario_services.service.ProductoService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService service;
    public ProductoController(ProductoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductoDTO>>> listar() {
        List<EntityModel<ProductoDTO>> productos = service.listar().stream()
                .map(p -> EntityModel.of(ProductoDTO.fromModel(p),
                        linkTo(methodOn(ProductoController.class).obtener(p.getId())).withSelfRel()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(productos, linkTo(methodOn(ProductoController.class).listar()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductoDTO>> obtener(@PathVariable Long id) {
        ProductoDTO dto = ProductoDTO.fromModel(service.obtenerPorId(id));
        return ResponseEntity.ok(EntityModel.of(dto,
                linkTo(methodOn(ProductoController.class).obtener(id)).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listar()).withRel("productos")));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ProductoDTO>> crear(@Valid @RequestBody ProductoDTO dto) {
        Producto p = service.guardar(dto.toModel());
        ProductoDTO response = ProductoDTO.fromModel(p);
        return ResponseEntity.ok(EntityModel.of(response, linkTo(methodOn(ProductoController.class).obtener(response.getId())).withSelfRel()));
    }
}