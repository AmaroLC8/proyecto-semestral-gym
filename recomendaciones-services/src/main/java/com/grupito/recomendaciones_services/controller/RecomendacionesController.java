package com.grupito.recomendaciones_services.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.recomendaciones_services.dto.RecomendacionesDTO;
import com.grupito.recomendaciones_services.services.RecomendacionesService;

@RestController
@RequestMapping("/recomendaciones")
public class RecomendacionesController {

    private static final Logger logger = LoggerFactory.getLogger(RecomendacionesController.class);

    private final RecomendacionesService recomendacionesService;

    public RecomendacionesController(RecomendacionesService recomendacionesService) {
        this.recomendacionesService = recomendacionesService;
    }

    @GetMapping
    public ResponseEntity<List<RecomendacionesDTO>> obtenerRecomendaciones() {
        logger.info("Solicitud para obtener recomendaciones de entrenamiento");
        List<RecomendacionesDTO> recomendaciones = recomendacionesService.obtenerRecomendaciones();
        return ResponseEntity.ok(recomendaciones);
    }

    @PostMapping
    public ResponseEntity<RecomendacionesDTO> crearRecomendacion(@RequestBody RecomendacionesDTO recomendacionesDto) {
        logger.info("Solicitud para crear recomendación: {}", recomendacionesDto.getMensaje());
        RecomendacionesDTO recomendacionGuardada = recomendacionesService.crearRecomendacion(recomendacionesDto);
        return ResponseEntity.ok(recomendacionGuardada);
    }
}
