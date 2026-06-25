package com.grupito.recomendaciones_services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupito.recomendaciones_services.model.Recomendaciones;

@Repository
public interface RecomendacionesRepository extends JpaRepository<Recomendaciones, Long> {

	List<Recomendaciones> findByMensajeContainingIgnoreCase(String texto);
	List<Recomendaciones> findTop5ByOrderByIdDesc();

}