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
    private Long idProducto;
    
    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "Mínimo 1 estrella")
    @Max(value = 5, message = "Máximo 5 estrellas")
    private Integer calificacion;
    
    @NotBlank(message = "El comentario no puede estar vacío")
    private String comentario;

    public Review toModel() {
        return new Review(id, idProducto, calificacion, comentario);
    }

    public static ReviewDTO fromModel(Review r) {
        if (r == null) return null;
        return new ReviewDTO(r.getId(), r.getIdProducto(), r.getCalificacion(), r.getComentario());
    }
}