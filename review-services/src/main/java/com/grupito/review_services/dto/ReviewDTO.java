package com.grupito.review_services.dto;

import com.grupito.review_services.model.Review;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;

    @NotNull(message = "El id del producto es obligatorio")
    @Min(value = 1, message = "El id del producto debe ser mayor a 0")
    private Long idProducto;

    @NotNull(message = "La calificacion es obligatoria")
    @Min(value = 1, message = "La calificacion minima es 1 estrella")
    @Max(value = 5, message = "La calificacion maxima es 5 estrellas")
    private Integer calificacion;

    @NotBlank(message = "El comentario no puede estar vacio")
    @Size(min = 10, max = 500, message = "El comentario debe tener entre 10 y 500 caracteres")
    private String comentario;

    public Review toModel() {
        return new Review(id, idProducto, calificacion, comentario);
    }

    public static ReviewDTO fromModel(Review r) {
        if (r == null) return null;
        return new ReviewDTO(r.getId(), r.getIdProducto(), r.getCalificacion(), r.getComentario());
    }
}