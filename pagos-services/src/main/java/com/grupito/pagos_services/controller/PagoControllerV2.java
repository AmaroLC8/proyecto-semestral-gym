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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pagos/v2")
@Tag(name = "Pagos V2 (HATEOAS)", description = "API de Pagos con soporte Hypermedia")
public class PagoControllerV2 {
    
    private final PagoService pagoService;
    private final PagoModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(PagoControllerV2.class);

    public PagoControllerV2(PagoService pagoService, PagoModelAssembler assembler) {
        this.pagoService = pagoService;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los pagos (V2)", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public CollectionModel<EntityModel<PagoDTO>> listarPagos() {
        logger.info("V2 GET /pagos - Listando pagos");
        List<EntityModel<PagoDTO>> pagos = pagoService.listar().stream()
                .map(PagoDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
                
        return CollectionModel.of(pagos, linkTo(methodOn(PagoControllerV2.class).listarPagos()).withSelfRel());
    }

    @Operation(summary = "Obtener un pago por ID (V2)", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public EntityModel<PagoDTO> obtenerPago(@PathVariable Long id) {
        logger.info("V2 GET /pagos/{} - Obteniendo pago", id);
        Pago pago = pagoService.obtenerPorId(id);
        return assembler.toModel(PagoDTO.fromModel(pago));
    }
}