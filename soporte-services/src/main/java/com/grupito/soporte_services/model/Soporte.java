package com.grupito.soporte_services.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "soporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Soporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long usuarioId;
    
    private String asunto;
    private String descripcion;
    private String estado; // Ejemplo: "PENDIENTE", "RESUELTO"
    
    private LocalDateTime fechaCreacion;
    
    @Column(length = 2000)
    private String respuestaAdmin;
}