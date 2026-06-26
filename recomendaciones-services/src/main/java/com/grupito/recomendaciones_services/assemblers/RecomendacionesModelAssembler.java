package com.grupito.recomendaciones_services.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grupito.recomendaciones_services.controller.RecomendacionesController;
import com.grupito.recomendaciones_services.dto.RecomendacionesDTO;

@Component
public class RecomendacionesModelAssembler
        implements RepresentationModelAssembler<RecomendacionesDTO, EntityModel<RecomendacionesDTO>> {

    @Override
    public EntityModel<RecomendacionesDTO> toModel(RecomendacionesDTO recomendacion) {
        return EntityModel.of(recomendacion,
                linkTo(methodOn(RecomendacionesController.class)
                        .obtenerPorId(recomendacion.getId())).withSelfRel(),
                linkTo(methodOn(RecomendacionesController.class)
                        .obtenerRecomendaciones()).withRel("recomendaciones"));
    }
}
