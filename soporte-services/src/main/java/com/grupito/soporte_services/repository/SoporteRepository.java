package com.grupito.soporte_services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupito.soporte_services.model.Soporte;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Long> {
	List<Soporte> findByUsuarioId(Long usuarioId);
	List<Soporte> findByEstado(String estado);
}