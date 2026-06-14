package com.grupito.rutinas_services.controller;

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

import com.grupito.rutinas_services.dto.RutinaDTO;
import com.grupito.rutinas_services.model.Rutina;
import com.grupito.rutinas_services.services.RutinaServices;

/**
 * Controlador REST para gestionar operaciones relacionadas con rutinas.
 * Proporciona endpoints para crear, listar y verificar la existencia de rutinas.
 */
@RestController
@RequestMapping("/rutinas")
public class RutinaController {
    private final RutinaServices rutinaServices;


    public RutinaController(RutinaServices rutinaServices) {
        this.rutinaServices = rutinaServices;
    }

    @PostMapping
    public ResponseEntity<RutinaDTO> crearRutina(@RequestBody RutinaDTO rutinaDto) {
        // Convierte el DTO a entidad y guarda en la base de datos
        Rutina nuevaRutina = rutinaServices.guardar(rutinaDto.toModel());
        // Convierte la entidad guardada de vuelta a DTO y retorna
        return ResponseEntity.ok(RutinaDTO.fromModel(nuevaRutina));
    }


    @GetMapping
    public ResponseEntity<List<RutinaDTO>> listarRutinas() {
        // Obtiene todas las rutinas del servicio
        List<Rutina> rutinas = rutinaServices.listar();
        // Convierte cada entidad a DTO usando stream
        List<RutinaDTO> dtos = rutinas.stream().map(RutinaDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeRutina(@PathVariable Long id) {
        // Verifica la existencia usando el servicio
        return ResponseEntity.ok(rutinaServices.existePorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutinaDTO> obtenerRutina(@PathVariable Long id) {
        Rutina rutina = rutinaServices.obtenerPorId(id);
        if (rutina != null) {
            return ResponseEntity.ok(RutinaDTO.fromModel(rutina));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutinaDTO> actualizarRutina(@PathVariable Long id, @RequestBody RutinaDTO rutinaDto) {
        Rutina rutinaExistente = rutinaServices.obtenerPorId(id);
        if (rutinaExistente != null) {
            rutinaDto.setId(id);
            Rutina rutinaActualizada = rutinaServices.actualizar(rutinaDto.toModel());
            return ResponseEntity.ok(RutinaDTO.fromModel(rutinaActualizada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRutina(@PathVariable Long id) {
        if (rutinaServices.existePorId(id)) {
            rutinaServices.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}