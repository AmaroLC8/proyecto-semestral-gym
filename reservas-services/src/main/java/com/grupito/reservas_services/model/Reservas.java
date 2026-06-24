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
    
    private Long idUsuario;
    private Long idProducto;
    private Date fechaReserva;
    private String estado; // Ejemplo: "PENDIENTE", "CONFIRMADA"
}