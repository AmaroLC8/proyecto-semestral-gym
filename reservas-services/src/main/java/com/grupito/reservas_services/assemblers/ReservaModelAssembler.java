package com.grupito.reservas_services.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grupito.reservas_services.controller.ReservaController;
import com.grupito.reservas_services.dto.ReservaDTO;

@Component
public class ReservaModelAssembler
        implements RepresentationModelAssembler<ReservaDTO, EntityModel<ReservaDTO>> {

    @Override
    public EntityModel<ReservaDTO> toModel(ReservaDTO reserva) {
        return EntityModel.of(reserva,
                linkTo(methodOn(ReservaController.class)
                        .obtener(reserva.getId())).withSelfRel(),
                linkTo(methodOn(ReservaController.class)
                        .listar()).withRel("reservas"));
    }
}
