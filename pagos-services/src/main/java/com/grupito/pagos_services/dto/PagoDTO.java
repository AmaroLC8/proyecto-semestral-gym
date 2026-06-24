package com.grupito.pagos_services.dto;

import com.grupito.pagos_services.model.Pago;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoDTO {
    private Long id;
    
    @NotNull(message = "El idCompra es obligatorio")
    private Long idCompra;
    
    @NotNull(message = "El valor neto es obligatorio")
    @Min(value = 100, message = "El valor neto mínimo es 100")
    @Max(value = 1000000, message = "El valor neto máximo es 1000000")
    private Integer valorNeto;
    
    @NotNull(message = "El descuento es obligatorio")
    @Min(value = 0, message = "El descuento mínimo es 0")
    @Max(value = 100, message = "El descuento máximo es 100")
    private Integer descuento;
    
    private Integer iva;
    private Integer totalPagar;
    
    @NotBlank(message = "El medio de pago es obligatorio")
    private String medioPago;
    
    private Date fecha;
    public Pago toModel() {
        return new Pago(id, idCompra, valorNeto, descuento, iva, totalPagar, medioPago, fecha);
    }

    public static PagoDTO fromModel(Pago p) {
        if (p == null) return null;
        return new PagoDTO(p.getId(), p.getIdCompra(), p.getValorNeto(), p.getDescuento(), p.getIva(), p.getTotalPagar(), p.getMedioPago(), p.getFecha());
    }
}