package com.grupito.rutinas_services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupito.rutinas_services.model.Rutina;

/**
 * Repositorio para la entidad Rutina.
 * Proporciona métodos CRUD básicos heredados de JpaRepository.
 */
@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Long>{

}
