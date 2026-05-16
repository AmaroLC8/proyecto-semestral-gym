package com.grupito.reservas_services.dto;

import java.util.Date;

import com.grupito.reservas_services.model.Reservas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para la entidad Reservas.
 * Se utiliza para transferir datos entre capas sin exponer la entidad directamente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDTO {
    private Long id;
    private int id_socio;
    private Date fecha_hora;
    private String tipo_clase;
    private String estado;

    /**
     * Convierte este DTO a una entidad Reservas.
     * @return Instancia de Reservas con los datos del DTO.
     */
    public Reservas toModel(){
        return new Reservas(id, id_socio, fecha_hora, tipo_clase, estado);
    }

    /**
     * Crea un DTO a partir de una entidad Reservas.
     * @param r La entidad Reservas a convertir.
     * @return DTO correspondiente, o null si la entidad es null.
     */
    public static ReservaDTO fromModel (Reservas r){
        if (r == null) return null;
        return new ReservaDTO(r.getId(), r.getIdSocio(), r.getFechaHora(), r.getTipoClase(), r.getEstado());
    }
}