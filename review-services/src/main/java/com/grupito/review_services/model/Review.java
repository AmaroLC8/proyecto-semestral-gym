package com.grupito.review_services.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long idProducto;
    
    @Column(nullable = false)
    private Integer calificacion;
    
    @Column(nullable = false)
    private String comentario;
}