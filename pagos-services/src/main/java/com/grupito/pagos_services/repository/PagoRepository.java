package com.grupito.pagos_services.repository;

import com.grupito.pagos_services.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByMedioPago(String medioPago);
    List<Pago> findByFechaBetween(Date desde, Date hasta);
    List<Pago> findByIdCompra(Long idCompra);
}