package com.grupito.pagos_services.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_compra")
    private Long idCompra;

    @Column(name = "valor_neto")
    private Integer valorNeto;

    private Integer iva;

    private Integer descuento;

    @Column(name = "total_pagar")
    private Integer totalPagar;

    @Column(name = "medio_pago")
    private String medioPago;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
}