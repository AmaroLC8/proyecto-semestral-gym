package com.grupito.recomendaciones_services.dto;

import com.grupito.recomendaciones_services.model.Recomendaciones;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecomendacionesDTO {

    private Long id;  

    @NotNull(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotNull(message = "El id del socio es obligatorio")
    private Integer idSocio;


    public Recomendaciones toModel() {
        return new Recomendaciones(id, mensaje, idSocio);
    }

    public static RecomendacionesDTO fromModel(Recomendaciones r) {
        if (r == null) return null;
        return new RecomendacionesDTO(r.getId(), r.getMensaje(), r.getIdSocio());
    }
}

