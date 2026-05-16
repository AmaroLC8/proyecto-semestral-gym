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

    /**
     * Convierte este DTO a una entidad Rutina.
     * @return Instancia de Rutina con los datos del DTO.
     */
    public Rutina toModel(){
        return new Rutina(id, nombre, descripcion, duracionMinutos, nivelDificultad);
    }

    /**
     * Crea un DTO a partir de una entidad Rutina.
     * @param r La entidad Rutina a convertir.
     * @return DTO correspondiente, o null si la entidad es null.
     */
    public static RutinaDTO fromModel (Rutina r){
        if (r == null) return null;
        return new RutinaDTO(r.getId(), r.getNombre(), r.getDescripcion(), r.getDuracionMinutos(), r.getNivelDificultad());
    }
}