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
    @Min(value = 1, message = "El id del socio debe ser mayor a 0")
    private Integer idSocio;

    @NotNull(message = "El peso es obligatorio")
    @DecimalMin(value = "20.0", message = "El peso minimo registrable es 20 kg")
    @DecimalMax(value = "300.0", message = "El peso maximo registrable es 300 kg")
    private Double peso;

    @NotNull(message = "El porcentaje de grasa es obligatorio")
    @DecimalMin(value = "0.0", message = "El porcentaje de grasa no puede ser negativo")
    @DecimalMax(value = "60.0", message = "El porcentaje de grasa no puede superar el 60%")
    private Double porcentajeGrasa;

    @PastOrPresent(message = "La fecha de registro no puede ser una fecha futura")
    private Date fechaRegistro;

    public Seguimiento toModel() {
        return new Seguimiento(id, idSocio, peso, porcentajeGrasa, fechaRegistro);
    }

    public static SeguimientoDTO fromModel(Seguimiento s) {
        if (s == null) return null;
        return new SeguimientoDTO(s.getId(), s.getIdSocio(), s.getPeso(), s.getPorcentajeGrasa(), s.getFechaRegistro());
    }
}