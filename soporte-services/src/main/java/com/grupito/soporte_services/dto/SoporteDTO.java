package com.grupito.soporte_services.dto;

import com.grupito.soporte_services.model.Soporte;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoporteDTO {
    private Long id;

    @NotNull(message = "El id de usuario es obligatorio")
    @Min(value = 1, message = "El id de usuario debe ser mayor a 0")
    private Long usuarioId;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(min = 5, max = 150, message = "El asunto debe tener entre 5 y 150 caracteres")
    private String asunto;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 20, max = 1000, message = "La descripcion debe tener entre 20 y 1000 caracteres")
    private String descripcion;

    public Soporte toModel() {
        return Soporte.builder()
                .usuarioId(usuarioId)
                .asunto(asunto)
                .descripcion(descripcion)
                .build();
    }

    public static SoporteDTO fromModel(Soporte s) {
        if (s == null) return null;
        return SoporteDTO.builder()
                .id(s.getId())
                .usuarioId(s.getUsuarioId())
                .asunto(s.getAsunto())
                .descripcion(s.getDescripcion())
                .build();
    }
}