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
    
    @NotNull @Min(1) @Max(5)
    private Integer calificacion;
    
    @NotBlank(message = "El comentario es obligatorio")
    private String comentario;

    public Review toModel() {
        return new Review(id, idProducto, calificacion, comentario);
    }

    public static ReviewDTO fromModel(Review r) {
        if (r == null) return null;
        return new ReviewDTO(r.getId(), r.getIdProducto(), r.getCalificacion(), r.getComentario());
    }
}