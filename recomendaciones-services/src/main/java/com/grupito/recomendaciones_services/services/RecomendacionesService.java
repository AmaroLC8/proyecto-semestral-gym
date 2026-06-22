package com.grupito.recomendaciones_services.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.grupito.recomendaciones_services.dto.RecomendacionesDTO;
import com.grupito.recomendaciones_services.model.Recomendaciones;
import com.grupito.recomendaciones_services.repository.RecomendacionesRepository;

@Service
public class RecomendacionesService {

    private final RecomendacionesRepository recomendacionesRepository;

    public RecomendacionesService(RecomendacionesRepository recomendacionesRepository) {
        this.recomendacionesRepository = recomendacionesRepository;
    }

    public List<RecomendacionesDTO> obtenerRecomendaciones() {
        return recomendacionesRepository.findAll().stream()
                .map(RecomendacionesDTO::fromModel)
                .collect(Collectors.toList());
    }

    public RecomendacionesDTO crearRecomendacion(RecomendacionesDTO dto) {
        Recomendaciones recomendacion = recomendacionesRepository.save(dto.toModel());
        return RecomendacionesDTO.fromModel(recomendacion);
    }
}

