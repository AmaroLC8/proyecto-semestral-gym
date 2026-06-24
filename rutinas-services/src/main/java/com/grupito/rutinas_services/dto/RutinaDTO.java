package com.grupito.rutinas_services.dto;

import com.grupito.rutinas_services.model.Rutina;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RutinaDTO {
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String descripcion;
    
    @Min(value = 1, message = "La duración debe ser mayor a 0")
    private int duracionMinutos;
    
    private String nivelDificultad;

    public Rutina toModel() {
        return new Rutina(id, nombre, descripcion, duracionMinutos, nivelDificultad);
    }

    public static RutinaDTO fromModel(Rutina r) {
        if (r == null) return null;
        return new RutinaDTO(r.getId(), r.getNombre(), r.getDescripcion(), r.getDuracionMinutos(), r.getNivelDificultad());
    }
}