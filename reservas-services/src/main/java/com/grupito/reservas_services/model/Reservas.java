package com.grupito.reservas_services.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long idUsuario;
    
    @Column(nullable = false)
    private Long idProducto;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReserva;
    
    @Column(nullable = false)
    private String estado;
}