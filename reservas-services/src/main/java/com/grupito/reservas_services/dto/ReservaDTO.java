package com.grupito.reservas_services.dto;

import com.grupito.reservas_services.model.Reservas;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDTO {
    private Long id;

    @NotNull(message = "El id de usuario es obligatorio")
    @Min(value = 1, message = "El id de usuario debe ser mayor a 0")
    private Long idUsuario;

    @NotNull(message = "El id de producto es obligatorio")
    @Min(value = 1, message = "El id de producto debe ser mayor a 0")
    private Long idProducto;

    @Future(message = "La fecha de reserva debe ser una fecha futura")
    private Date fechaReserva;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(PENDIENTE|CONFIRMADA|CANCELADA)$",
             message = "El estado debe ser: PENDIENTE, CONFIRMADA o CANCELADA")
    private String estado;

    public Reservas toModel() {
        return new Reservas(id, idUsuario, idProducto, fechaReserva, estado);
    }

    public static ReservaDTO fromModel(Reservas r) {
        if (r == null) return null;
        return new ReservaDTO(r.getId(), r.getIdUsuario(), r.getIdProducto(), r.getFechaReserva(), r.getEstado());
    }
}