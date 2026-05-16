package com.grupito.rutinas_services.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una rutina de ejercicios en la base de datos.
 * Mapeada a una tabla en JPA con anotaciones.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private int duracionMinutos;
    private String nivelDificultad;
}
