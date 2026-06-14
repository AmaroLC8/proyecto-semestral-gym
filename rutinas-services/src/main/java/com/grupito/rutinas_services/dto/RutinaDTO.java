package com.grupito.rutinas_services.dto;

import com.grupito.rutinas_services.model.Rutina;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para la entidad Rutina.
 * Se utiliza para transferir datos entre capas sin exponer la entidad directamente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RutinaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private int duracionMinutos;
    private String nivelDificultad;

    public Rutina toModel(){
        return new Rutina(id, nombre, descripcion, duracionMinutos, nivelDificultad);
    }

    public static RutinaDTO fromModel (Rutina r){
        if (r == null) return null;
        return new RutinaDTO(r.getId(), r.getNombre(), r.getDescripcion(), r.getDuracionMinutos(), r.getNivelDificultad());
    }
}