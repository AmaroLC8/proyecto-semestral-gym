package com.grupito.recomendaciones_services.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recomendaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recomendaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false, name = "id_socio")
    private Integer idSocio;
}

