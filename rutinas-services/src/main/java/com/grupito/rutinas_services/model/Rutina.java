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
    /**
     * Identificador único de la rutina, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la rutina.
     */
    private String nombre;

    /**
     * Descripción detallada de la rutina.
     */
    private String descripcion;

    /**
     * Duración estimada de la rutina en minutos.
     */
    private int duracionMinutos;

    /**
     * Nivel de dificultad de la rutina (ej. principiante, intermedio, avanzado).
     */
    private String nivelDificultad;
}
