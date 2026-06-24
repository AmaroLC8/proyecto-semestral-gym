package com.grupito.pagos_services.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import com.grupito.pagos_services.dto.PagoDTO;
import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.services.PagoService;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<EntityModel<PagoDTO>> crearPago(@Valid @RequestBody PagoDTO pagoDto) {
        Pago nuevoPago = pagoService.procesarYGuardarPago(pagoDto.toModel());
        PagoDTO responseDto = PagoDTO.fromModel(nuevoPago);

        EntityModel<PagoDTO> resource = EntityModel.of(responseDto,
                linkTo(methodOn(PagoController.class).obtenerPago(responseDto.getId())).withSelfRel(),
                linkTo(methodOn(PagoController.class).listarPagos()).withRel("pagos"));

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> listarPagos() {
        List<EntityModel<PagoDTO>> pagos = pagoService.listar().stream()
                .map(p -> EntityModel.of(PagoDTO.fromModel(p),
                        linkTo(methodOn(PagoController.class).obtenerPago(p.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(pagos,
                linkTo(methodOn(PagoController.class).listarPagos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PagoDTO>> obtenerPago(@PathVariable Long id) {
        PagoDTO dto = PagoDTO.fromModel(pagoService.obtenerPorId(id));
        return ResponseEntity.ok(EntityModel.of(dto,
                linkTo(methodOn(PagoController.class).obtenerPago(id)).withSelfRel(),
                linkTo(methodOn(PagoController.class).listarPagos()).withRel("pagos")));
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existePago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.existePorId(id));
    }

    @GetMapping("/metodo/{metodo}")
    public ResponseEntity<List<PagoDTO>> buscarPagosPorMetodo(@PathVariable String metodo) {
        List<Pago> pagos = pagoService.buscarPorMetodo(metodo);
        List<PagoDTO> dtos = pagos.stream().map(PagoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<PagoDTO>> buscarPagosPorRangoFechas(
            @RequestParam("desde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date desde,
            @RequestParam("hasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date hasta) {
        List<Pago> pagos = pagoService.buscarPorRangoFechas(desde, hasta);
        List<PagoDTO> dtos = pagos.stream().map(PagoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/compra/{idCompra}")
    public ResponseEntity<List<PagoDTO>> buscarPagosPorCompra(@PathVariable Long idCompra) {
        List<Pago> pagos = pagoService.buscarPorIdCompra(idCompra);
        List<PagoDTO> dtos = pagos.stream().map(PagoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<Map<String, Object>> obtenerPagoConUsuarioYReservas(@PathVariable Long id) {
        Pago pago = pagoService.obtenerPorId(id);
        if (pago != null) {
            Map<String, Object> detalle = new HashMap<>();
            detalle.put("pago", PagoDTO.fromModel(pago));
            detalle.put("usuarioRemoto", pagoService.obtenerUsuarioRemoto(pago.getIdCompra())); 
            detalle.put("reservasRemotas", pagoService.obtenerReservasRemotas(pago.getIdCompra()));
            return ResponseEntity.ok(detalle);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/compra/{idCompra}/total")
    public ResponseEntity<Double> obtenerTotalPagosPorCompra(@PathVariable Long idCompra) {
        List<Pago> pagos = pagoService.buscarPorIdCompra(idCompra);
        double total = pagos.stream().mapToDouble(Pago::getTotalPagar).sum();
        return ResponseEntity.ok(total);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> actualizarPago(@PathVariable Long id, @Valid @RequestBody PagoDTO pagoDto) {
        pagoDto.setId(id);
        Pago actualizado = pagoService.actualizar(pagoDto.toModel());
        return ResponseEntity.ok(PagoDTO.fromModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}