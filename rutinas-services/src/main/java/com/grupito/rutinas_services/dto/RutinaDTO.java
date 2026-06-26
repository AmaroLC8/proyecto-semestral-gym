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

    @NotBlank(message = "El nombre de la rutina es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripcion no puede superar los 500 caracteres")
    private String descripcion;

    @Min(value = 10, message = "La duracion minima es de 10 minutos")
    @Max(value = 300, message = "La duracion maxima es de 300 minutos (5 horas)")
    private int duracionMinutos;

    @NotBlank(message = "El nivel de dificultad es obligatorio")
    @Pattern(regexp = "^(PRINCIPIANTE|INTERMEDIO|AVANZADO)$",
             message = "El nivel debe ser: PRINCIPIANTE, INTERMEDIO o AVANZADO")
    private String nivelDificultad;

    public Rutina toModel() {
        return new Rutina(id, nombre, descripcion, duracionMinutos, nivelDificultad);
    }

    public static RutinaDTO fromModel(Rutina r) {
        if (r == null) return null;
        return new RutinaDTO(r.getId(), r.getNombre(), r.getDescripcion(), r.getDuracionMinutos(), r.getNivelDificultad());
    }
}