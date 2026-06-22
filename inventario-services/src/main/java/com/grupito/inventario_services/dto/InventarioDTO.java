package com.grupito.inventario_services.dto;

import com.grupito.inventario_services.model.Inventarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private Boolean disponible;
    private String categoria;

    public Inventarios toModel() {
        return new Inventarios(id, nombre, descripcion, cantidad, disponible, categoria);
    }

    public static InventarioDTO fromModel(Inventarios inventario) {
        if (inventario == null) {
            return null;
        }
        return new InventarioDTO(
                inventario.getId(),
                inventario.getNombre(),
                inventario.getDescripcion(),
                inventario.getCantidad(),
                inventario.getDisponible(),
                inventario.getCategoria());
    }
}
