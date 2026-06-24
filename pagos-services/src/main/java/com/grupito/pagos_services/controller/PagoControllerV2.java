package com.grupito.pagos_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.pagos_services.assemblers.PagoModelAssembler;
import com.grupito.pagos_services.dto.PagoDTO;
import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.services.PagoService;

@RestController
@RequestMapping("/pagos/v2")
public class PagoControllerV2 {
    
    private final PagoService pagoService;
    private final PagoModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(PagoControllerV2.class);

    public PagoControllerV2(PagoService pagoService, PagoModelAssembler assembler) {
        this.pagoService = pagoService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<PagoDTO>> listarPagos() {
        logger.info("V2 GET /pagos - Listando pagos");
        List<EntityModel<PagoDTO>> pagos = pagoService.listar().stream()
                .map(PagoDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pagos, linkTo(methodOn(PagoControllerV2.class).listarPagos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<PagoDTO> obtenerPago(@PathVariable Long id) {
        logger.info("V2 GET /pagos/{} - Obteniendo pago", id);
        Pago pago = pagoService.obtenerPorId(id);
        return assembler.toModel(PagoDTO.fromModel(pago));
    }
}