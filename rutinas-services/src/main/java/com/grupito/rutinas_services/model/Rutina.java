package com.grupito.rutinas_services.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rutinas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    private int duracionMinutos;
    private String nivelDificultad;
}