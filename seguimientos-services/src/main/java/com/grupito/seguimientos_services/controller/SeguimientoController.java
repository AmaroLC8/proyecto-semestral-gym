package com.grupito.seguimientos_services.controller;

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

import com.grupito.seguimientos_services.dto.SeguimientoDTO;
import com.grupito.seguimientos_services.model.Seguimiento;
import com.grupito.seguimientos_services.services.SeguimientoService;

/**
 * Controlador REST para gestionar operaciones relacionadas con seguimientos.
 * Proporciona endpoints para crear, listar y verificar la existencia de seguimientos.
 */
@RestController
@RequestMapping("/seguimientos")
public class SeguimientoController {
    private final SeguimientoService seguimientoService;

    public SeguimientoController(SeguimientoService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    @PostMapping
    public ResponseEntity<SeguimientoDTO> crearSeguimiento(@RequestBody SeguimientoDTO seguimientoDto) {
        // Convierte el DTO a entidad y guarda en la base de datos
        Seguimiento nuevoSeguimiento = seguimientoService.guardar(seguimientoDto.toModel());
        // Convierte la entidad guardada de vuelta a DTO y retorna
        return ResponseEntity.ok(SeguimientoDTO.fromModel(nuevoSeguimiento));
    }

    @GetMapping
    public ResponseEntity<List<SeguimientoDTO>> listarSeguimientos() {
        // Obtiene todos los seguimientos del servicio
        List<Seguimiento> seguimientos = seguimientoService.listar();
        // Convierte cada entidad a DTO usando stream
        List<SeguimientoDTO> dtos = seguimientos.stream().map(SeguimientoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeSeguimiento(@PathVariable Long id) {
        // Verifica la existencia usando el servicio
        return ResponseEntity.ok(seguimientoService.existePorId(id));
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<SeguimientoDTO>> buscarSeguimientosPorRangoFechas(
            @RequestParam("desde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date desde,
            @RequestParam("hasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date hasta) {
        List<Seguimiento> seguimientos = seguimientoService.buscarPorRangoFechas(desde, hasta);
        List<SeguimientoDTO> dtos = seguimientos.stream().map(SeguimientoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/socio/{idSocio}")
    public ResponseEntity<List<SeguimientoDTO>> buscarSeguimientosPorSocio(@PathVariable int idSocio) {
        List<Seguimiento> seguimientos = seguimientoService.buscarPorIdSocio(idSocio);
        List<SeguimientoDTO> dtos = seguimientos.stream().map(SeguimientoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<Map<String, Object>> obtenerSeguimientoConUsuario(@PathVariable Long id) {
        Seguimiento seguimiento = seguimientoService.obtenerPorId(id);
        if (seguimiento != null) {
            Map<String, Object> detalle = new HashMap<>();
            detalle.put("seguimiento", SeguimientoDTO.fromModel(seguimiento));
            detalle.put("usuario", seguimientoService.obtenerUsuarioPorId((long) seguimiento.getId_socio()));
            return ResponseEntity.ok(detalle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/socio/{idSocio}/promedio-peso")
    public ResponseEntity<Double> obtenerPromedioPesoPorSocio(@PathVariable int idSocio) {
        List<Seguimiento> seguimientos = seguimientoService.buscarPorIdSocio(idSocio);
        double promedio = seguimientos.stream().mapToDouble(Seguimiento::getPeso).average().orElse(0.0);
        return ResponseEntity.ok(promedio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoDTO> obtenerSeguimiento(@PathVariable Long id) {
        Seguimiento seguimiento = seguimientoService.obtenerPorId(id);
        if (seguimiento != null) {
            return ResponseEntity.ok(SeguimientoDTO.fromModel(seguimiento));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeguimientoDTO> actualizarSeguimiento(@PathVariable Long id, @RequestBody SeguimientoDTO seguimientoDto) {
        Seguimiento seguimientoExistente = seguimientoService.obtenerPorId(id);
        if (seguimientoExistente != null) {
            seguimientoDto.setId(id);
            Seguimiento seguimientoActualizado = seguimientoService.actualizar(seguimientoDto.toModel());
            return ResponseEntity.ok(SeguimientoDTO.fromModel(seguimientoActualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSeguimiento(@PathVariable Long id) {
        if (seguimientoService.existePorId(id)) {
            seguimientoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}