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
    
    @NotNull(message = "Usuario obligatorio")
    private Long idUsuario;
    
    @NotNull(message = "Producto obligatorio")
    private Long idProducto;
    
    private Date fechaReserva;
    
    @NotBlank(message = "Estado obligatorio")
    private String estado;

    public Reservas toModel() {
        return new Reservas(id, idUsuario, idProducto, fechaReserva, estado);
    }

    public static ReservaDTO fromModel(Reservas r) {
        if (r == null) return null;
        return new ReservaDTO(r.getId(), r.getIdUsuario(), r.getIdProducto(), r.getFechaReserva(), r.getEstado());
    }
}