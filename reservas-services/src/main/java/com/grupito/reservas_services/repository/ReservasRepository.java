package com.grupito.reservas_services.repository;

import com.grupito.reservas_services.model.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Long> {
    List<Reservas> findByIdUsuario(Long idUsuario);
}