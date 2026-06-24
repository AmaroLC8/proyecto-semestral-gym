package com.grupito.seguimientos_services.dto;

import com.grupito.seguimientos_services.model.Seguimiento;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguimientoDTO {
    private Long id;
    
    @NotNull(message = "El id del socio es obligatorio")
    private Integer idSocio;
    
    @NotNull @Positive(message = "El peso debe ser mayor a 0")
    private Double peso;
    
    @NotNull @Min(0) @Max(100)
    private Double porcentajeGrasa;
    
    private Date fechaRegistro;

    public Seguimiento toModel() {
        return new Seguimiento(id, idSocio, peso, porcentajeGrasa, fechaRegistro);
    }

    public static SeguimientoDTO fromModel(Seguimiento s) {
        if (s == null) return null;
        return new SeguimientoDTO(s.getId(), s.getIdSocio(), s.getPeso(), s.getPorcentajeGrasa(), s.getFechaRegistro());
    }
}