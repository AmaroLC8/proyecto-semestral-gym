package com.grupito.pagos_services.dto;

import java.util.Date;

import com.grupito.pagos_services.model.Pago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoDTO {
    private Long id;
    private int id_socio;
    private double monto;
    private Date fecha_pago;
    private String metodo_pago;

    public Pago toModel(){
        return new Pago(id, id_socio, monto, fecha_pago, metodo_pago);
    }

    public static PagoDTO fromModel (Pago p){
        if (p == null) return null;
        return new PagoDTO(p.getId(), p.getIdSocio(), p.getMonto(), p.getFechaPago(), p.getMetodoPago());
    }
}