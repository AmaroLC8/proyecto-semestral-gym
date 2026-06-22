package com.grupito.recomendaciones_services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupito.recomendaciones_services.model.Recomendaciones;

@Repository
public interface RecomendacionesRepository extends JpaRepository<Recomendaciones, Long> {

	// Busca recomendaciones que contengan el texto (sin distinguir mayúsculas/minúsculas)
	List<Recomendaciones> findByMensajeContainingIgnoreCase(String texto);

	// Obtiene las últimas 5 recomendaciones por id (útil para mostrar recientes)
	List<Recomendaciones> findTop5ByOrderByIdDesc();

}