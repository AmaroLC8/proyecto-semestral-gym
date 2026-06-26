package com.grupito.inventario_services.dto;

import com.grupito.inventario_services.model.Producto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    public Producto toModel() {
        return new Producto(id, nombre, stock, precio, categoria);
    }

    public static ProductoDTO fromModel(Producto p) {
        if (p == null) return null;
        return new ProductoDTO(p.getId(), p.getNombre(), p.getStock(), p.getPrecio(), p.getCategoria());
    }
}