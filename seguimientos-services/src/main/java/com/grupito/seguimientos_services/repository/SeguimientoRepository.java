package com.grupito.seguimientos_services.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupito.seguimientos_services.model.Seguimiento;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

    List<Seguimiento> findByIdSocio(int idSocio);

    List<Seguimiento> findByFechaRegistroBetween(Date desde, Date hasta);
}