package com.grupito.rutinas_services.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grupito.rutinas_services.controller.RutinaController;
import com.grupito.rutinas_services.dto.RutinaDTO;

@Component
public class RutinaModelAssembler
        implements RepresentationModelAssembler<RutinaDTO, EntityModel<RutinaDTO>> {

    @Override
    public EntityModel<RutinaDTO> toModel(RutinaDTO rutina) {
        return EntityModel.of(rutina,
                linkTo(methodOn(RutinaController.class)
                        .obtener(rutina.getId())).withSelfRel(),
                linkTo(methodOn(RutinaController.class)
                        .listar()).withRel("rutinas"));
    }
}
