package com.grupito.pagos_services.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.pagos_services.dto.PagoDTO;
import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.services.PagoService;

/**
 * Controlador REST para gestionar operaciones relacionadas con pagos.
 * Proporciona endpoints para crear, listar y verificar la existencia de pagos.
 */
@RestController
@RequestMapping("/pagos")
public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<PagoDTO> crearPago(@RequestBody PagoDTO pagoDto) {
        // Convierte el DTO a entidad y guarda en la base de datos
        Pago nuevoPago = pagoService.guardar(pagoDto.toModel());
        // Convierte la entidad guardada de vuelta a DTO y retorna
        return ResponseEntity.ok(PagoDTO.fromModel(nuevoPago));
    }

    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarPagos() {
        // Obtiene todos los pagos del servicio
        List<Pago> pagos = pagoService.listar();
        // Convierte cada entidad a DTO usando stream
        List<PagoDTO> dtos = pagos.stream().map(PagoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existePago(@PathVariable Long id) {
        // Verifica la existencia usando el servicio
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

    @GetMapping("/socio/{idSocio}")
    public ResponseEntity<List<PagoDTO>> buscarPagosPorSocio(@PathVariable int idSocio) {
        List<Pago> pagos = pagoService.buscarPorIdSocio(idSocio);
        List<PagoDTO> dtos = pagos.stream().map(PagoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<Map<String, Object>> obtenerPagoConUsuario(@PathVariable Long id) {
        Pago pago = pagoService.obtenerPorId(id);
        if (pago != null) {
            Map<String, Object> detalle = new HashMap<>();
            detalle.put("pago", PagoDTO.fromModel(pago));
            detalle.put("usuario", pagoService.obtenerUsuarioPorId((long) pago.getIdSocio()));
            return ResponseEntity.ok(detalle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/socio/{idSocio}/total")
    public ResponseEntity<Double> obtenerTotalPagosPorSocio(@PathVariable int idSocio) {
        List<Pago> pagos = pagoService.buscarPorIdSocio(idSocio);
        double total = pagos.stream().mapToDouble(Pago::getMonto).sum();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPago(@PathVariable Long id) {
        Pago pago = pagoService.obtenerPorId(id);
        if (pago != null) {
            return ResponseEntity.ok(PagoDTO.fromModel(pago));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> actualizarPago(@PathVariable Long id, @RequestBody PagoDTO pagoDto) {
        Pago pagoExistente = pagoService.obtenerPorId(id);
        if (pagoExistente != null) {
            pagoDto.setId(id); // Asegurar que el ID sea el correcto
            Pago pagoActualizado = pagoService.actualizar(pagoDto.toModel());
            return ResponseEntity.ok(PagoDTO.fromModel(pagoActualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        if (pagoService.existePorId(id)) {
            pagoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}