package com.grupito.reservas_services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupito.reservas_services.model.Reservas;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Long>{

    List<Reservas> findByIdSocio(int idSocio);
}
