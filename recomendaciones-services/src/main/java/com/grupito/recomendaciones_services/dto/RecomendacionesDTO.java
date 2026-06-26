package com.grupito.recomendaciones_services.dto;

import com.grupito.recomendaciones_services.model.Recomendaciones;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecomendacionesDTO {

    private Long id;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 10, max = 500, message = "El mensaje debe tener entre 10 y 500 caracteres")
    private String mensaje;

    @NotNull(message = "El id del socio es obligatorio")
    @Min(value = 1, message = "El id del socio debe ser mayor a 0")
    private Integer idSocio;

    public Recomendaciones toModel() {
        return new Recomendaciones(id, mensaje, idSocio);
    }

    public static RecomendacionesDTO fromModel(Recomendaciones r) {
        if (r == null) return null;
        return new RecomendacionesDTO(r.getId(), r.getMensaje(), r.getIdSocio());
    }
}
