package com.grupito.reservas_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.reservas_services.dto.ReservaDTO;
import com.grupito.reservas_services.model.Reservas;
import com.grupito.reservas_services.services.ReservaService;

/**
 * Controlador REST para gestionar operaciones relacionadas con reservas.
 * Proporciona endpoints para crear, listar y verificar la existencia de reservas.
 */
@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> crearReserva(@RequestBody ReservaDTO reservaDto) {
        // Convierte el DTO a entidad y guarda en la base de datos
        Reservas nuevaReserva = reservaService.guardar(reservaDto.toModel());
        // Convierte la entidad guardada de vuelta a DTO y retorna
        return ResponseEntity.ok(ReservaDTO.fromModel(nuevaReserva));
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listarReservas() {
        // Obtiene todas las reservas del servicio
        List<Reservas> reservas = reservaService.listar();
        // Convierte cada entidad a DTO usando stream
        List<ReservaDTO> dtos = reservas.stream().map(ReservaDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeReserva(@PathVariable Long id) {
        // Verifica la existencia usando el servicio
        return ResponseEntity.ok(reservaService.existePorId(id));
    }

    @GetMapping("/por-socio/{idSocio}")
    public ResponseEntity<List<ReservaDTO>> buscarReservasPorSocio(@PathVariable int idSocio) {
        List<Reservas> reservas = reservaService.buscarPorIdSocio(idSocio);
        List<ReservaDTO> dtos = reservas.stream().map(ReservaDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> obtenerReserva(@PathVariable Long id) {
        Reservas reserva = reservaService.obtenerPorId(id);
        if (reserva != null) {
            return ResponseEntity.ok(ReservaDTO.fromModel(reserva));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> actualizarReserva(@PathVariable Long id, @RequestBody ReservaDTO reservaDto) {
        Reservas reservaExistente = reservaService.obtenerPorId(id);
        if (reservaExistente != null) {
            reservaDto.setId(id);
            Reservas reservaActualizada = reservaService.actualizar(reservaDto.toModel());
            return ResponseEntity.ok(ReservaDTO.fromModel(reservaActualizada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        if (reservaService.existePorId(id)) {
            reservaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
