package com.grupito.pagos_services.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grupito.pagos_services.controller.PagoControllerV2;
import com.grupito.pagos_services.dto.PagoDTO;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoDTO, EntityModel<PagoDTO>> {

    @Override
    public EntityModel<PagoDTO> toModel(PagoDTO pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoControllerV2.class).obtenerPago(pago.getId())).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).listarPagos()).withRel("pagos"));
    }
}