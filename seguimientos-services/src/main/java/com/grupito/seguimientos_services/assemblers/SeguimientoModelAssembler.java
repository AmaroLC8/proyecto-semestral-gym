package com.grupito.seguimientos_services.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grupito.seguimientos_services.controller.SeguimientoController;
import com.grupito.seguimientos_services.dto.SeguimientoDTO;

@Component
public class SeguimientoModelAssembler
        implements RepresentationModelAssembler<SeguimientoDTO, EntityModel<SeguimientoDTO>> {

    @Override
    public EntityModel<SeguimientoDTO> toModel(SeguimientoDTO seguimiento) {
        return EntityModel.of(seguimiento,
                linkTo(methodOn(SeguimientoController.class)
                        .obtener(seguimiento.getId())).withSelfRel(),
                linkTo(methodOn(SeguimientoController.class)
                        .listar()).withRel("seguimientos"));
    }
}
