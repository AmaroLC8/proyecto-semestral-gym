package com.grupito.seguimientos_services.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_socio")
    private int idSocio;
    
    private double peso;
    
    @Column(name = "porcentaje_grasa")
    private double porcentajeGrasa;
    
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
}
