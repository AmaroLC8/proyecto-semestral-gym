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
    private Long usuarioId;
    
    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;
    
    @NotBlank(message = "La descripción es obligatoria")
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