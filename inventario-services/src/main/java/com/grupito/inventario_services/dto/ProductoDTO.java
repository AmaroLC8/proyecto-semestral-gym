package com.grupito.inventario_services.dto;

import com.grupito.inventario_services.model.Producto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Max(value = 10000, message = "El stock no puede superar las 10.000 unidades")
    private Integer stock;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @DecimalMax(value = "100000000.0", message = "El precio no puede superar los 100.000.000")
    private Double precio;

    @NotBlank(message = "La categoria es obligatoria")
    @Pattern(regexp = "^(EQUIPAMIENTO|SUPLEMENTO|ACCESORIO|ROPA|SERVICIO)$",
             message = "La categoria debe ser: EQUIPAMIENTO, SUPLEMENTO, ACCESORIO, ROPA o SERVICIO")
    private String categoria;

    public Producto toModel() {
        return new Producto(id, nombre, stock, precio, categoria);
    }

    public static ProductoDTO fromModel(Producto p) {
        if (p == null) return null;
        return new ProductoDTO(p.getId(), p.getNombre(), p.getStock(), p.getPrecio(), p.getCategoria());
    }
}