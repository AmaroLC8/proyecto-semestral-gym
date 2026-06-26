package com.grupito.inventario_services.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grupito.inventario_services.controller.ProductoController;
import com.grupito.inventario_services.dto.ProductoDTO;

@Component
public class ProductoModelAssembler
        implements RepresentationModelAssembler<ProductoDTO, EntityModel<ProductoDTO>> {

    @Override
    public EntityModel<ProductoDTO> toModel(ProductoDTO producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class)
                        .obtener(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class)
                        .listar()).withRel("productos"));
    }
}
