package com.grupito.seguimientos_services.dto;

import java.util.Date;

import com.grupito.seguimientos_services.model.Seguimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para la entidad Seguimiento.
 * Se utiliza para transferir datos entre capas sin exponer la entidad directamente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguimientoDTO {
    private Long id;
    private int id_socio;
    private double peso;
    private double porcentaje_grasa;
    private Date fecha_registro;

    /**
     * Convierte este DTO a una entidad Seguimiento.
     * @return Instancia de Seguimiento con los datos del DTO.
     */
    public Seguimiento toModel(){
        return new Seguimiento(id, id_socio, peso, porcentaje_grasa, fecha_registro);
    }

    /**
     * Crea un DTO a partir de una entidad Seguimiento.
     * @param s La entidad Seguimiento a convertir.
     * @return DTO correspondiente, o null si la entidad es null.
     */
    public static SeguimientoDTO fromModel (Seguimiento s){
        if (s == null) return null;
        return new SeguimientoDTO(s.getId(), s.getId_socio(), s.getPeso(), s.getPorcentaje_grasa(), s.getFecha_registro());
    }
}