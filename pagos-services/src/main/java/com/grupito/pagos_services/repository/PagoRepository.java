package com.grupito.pagos_services.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupito.pagos_services.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByIdSocio(int idSocio);

    List<Pago> findByMetodoPago(String metodoPago);

    List<Pago> findByFechaPagoBetween(Date desde, Date hasta);
}