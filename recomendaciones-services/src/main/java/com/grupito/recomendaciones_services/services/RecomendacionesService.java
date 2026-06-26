package com.grupito.recomendaciones_services.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.grupito.recomendaciones_services.dto.RecomendacionesDTO;
import com.grupito.recomendaciones_services.exception.BadRequestException;
import com.grupito.recomendaciones_services.exception.ResourceNotFoundException;
import com.grupito.recomendaciones_services.model.Recomendaciones;
import com.grupito.recomendaciones_services.repository.RecomendacionesRepository;

@Service
public class RecomendacionesService {

    private final RecomendacionesRepository recomendacionesRepository;

    private static final int MAX_RECOMENDACIONES_POR_SOCIO = 10;
    private static final int LONGITUD_MINIMA_MENSAJE = 10;
    private static final String[] PALABRAS_PROHIBIDAS = {"spam", "publicidad", "oferta", "compra ahora"};

    public RecomendacionesService(RecomendacionesRepository recomendacionesRepository) {
        this.recomendacionesRepository = recomendacionesRepository;
    }

    public List<RecomendacionesDTO> obtenerRecomendaciones() {
        return recomendacionesRepository.findAll().stream()
                .map(RecomendacionesDTO::fromModel)
                .collect(Collectors.toList());
    }

    public RecomendacionesDTO obtenerPorId(Long id) {
        Recomendaciones r = recomendacionesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recomendacion no encontrada con id: " + id));
        return RecomendacionesDTO.fromModel(r);
    }

    /**
     * Crea una recomendacion aplicando las siguientes reglas de negocio:
     *
     * REGLA 1: El mensaje debe tener una longitud minima de 10 caracteres.
     *          Mensajes demasiado cortos no aportan valor como recomendacion.
     *
     * REGLA 2: El mensaje no puede contener palabras prohibidas asociadas a publicidad
     *          o spam (spam, publicidad, oferta, compra ahora). Las recomendaciones
     *          deben ser de caracter deportivo/salud.
     *
     * REGLA 3: Un socio no puede tener mas de 10 recomendaciones activas en el sistema.
     *          Esto evita la saturacion de contenido y fomenta la calidad sobre la cantidad.
     */
    public RecomendacionesDTO crearRecomendacion(RecomendacionesDTO dto) {

        // REGLA 1: Longitud minima del mensaje
        if (dto.getMensaje() == null || dto.getMensaje().trim().length() < LONGITUD_MINIMA_MENSAJE) {
            throw new BadRequestException(
                    "El mensaje de la recomendacion debe tener al menos " + LONGITUD_MINIMA_MENSAJE + " caracteres.");
        }

        // REGLA 2: Contenido sin palabras prohibidas
        String mensajeLower = dto.getMensaje().toLowerCase();
        for (String palabraProhibida : PALABRAS_PROHIBIDAS) {
            if (mensajeLower.contains(palabraProhibida)) {
                throw new BadRequestException(
                        "El mensaje contiene contenido no permitido (publicidad o spam). " +
                        "Las recomendaciones deben ser de caracter deportivo o de salud.");
            }
        }

        // REGLA 3: Limite de recomendaciones por socio
        long recomendacionesSocio = recomendacionesRepository.findAll().stream()
                .filter(r -> r.getIdSocio().equals(dto.getIdSocio()))
                .count();
        if (recomendacionesSocio >= MAX_RECOMENDACIONES_POR_SOCIO) {
            throw new BadRequestException(
                    "El socio con id " + dto.getIdSocio() +
                    " ya tiene " + MAX_RECOMENDACIONES_POR_SOCIO +
                    " recomendaciones. No se pueden agregar mas.");
        }

        Recomendaciones recomendacion = recomendacionesRepository.save(dto.toModel());
        return RecomendacionesDTO.fromModel(recomendacion);
    }

    public void eliminar(Long id) {
        recomendacionesRepository.deleteById(id);
    }
}
