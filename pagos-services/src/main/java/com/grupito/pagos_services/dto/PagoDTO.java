package com.grupito.pagos_services.dto;

import java.util.Date;

import com.grupito.pagos_services.model.Pago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para la entidad Pago.
 * Se utiliza para transferir datos entre capas sin exponer la entidad directamente.
 */
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

    /**
     * Convierte este DTO a una entidad Pago.
     * @return Instancia de Pago con los datos del DTO.
     */
    public Pago toModel(){
        return new Pago(id, id_socio, monto, fecha_pago, metodo_pago);
    }

    /**
     * Crea un DTO a partir de una entidad Pago.
     * @param p La entidad Pago a convertir.
     * @return DTO correspondiente, o null si la entidad es null.
     */
    public static PagoDTO fromModel (Pago p){
        if (p == null) return null;
        return new PagoDTO(p.getId(), p.getId_socio(), p.getMonto(), p.getFecha_pago(), p.getMetodo_pago());
    }
}