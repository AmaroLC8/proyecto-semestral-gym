package com.grupito.inventario_services.dto;

import com.grupito.inventario_services.model.Producto;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;
    private String nombre;
    private Integer stock;
    private Double precio;
    private String categoria;

    public Producto toModel() {
        return new Producto(id, nombre, stock, precio, categoria);
    }

    public static ProductoDTO fromModel(Producto p) {
        if (p == null) return null;
        return new ProductoDTO(p.getId(), p.getNombre(), p.getStock(), p.getPrecio(), p.getCategoria());
    }
}