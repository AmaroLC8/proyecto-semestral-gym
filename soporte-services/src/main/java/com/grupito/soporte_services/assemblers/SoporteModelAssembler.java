package com.grupito.soporte_services.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grupito.soporte_services.controller.SoporteController;
import com.grupito.soporte_services.dto.SoporteDTO;

@Component
public class SoporteModelAssembler
        implements RepresentationModelAssembler<SoporteDTO, EntityModel<SoporteDTO>> {

    @Override
    public EntityModel<SoporteDTO> toModel(SoporteDTO soporte) {
        return EntityModel.of(soporte,
                linkTo(methodOn(SoporteController.class)
                        .obtenerPorId(soporte.getId())).withSelfRel(),
                linkTo(methodOn(SoporteController.class)
                        .listarPorUsuario(soporte.getUsuarioId())).withRel("soporte-usuario"),
                linkTo(methodOn(SoporteController.class)
                        .listar()).withRel("soporte-collection"));
    }
}
