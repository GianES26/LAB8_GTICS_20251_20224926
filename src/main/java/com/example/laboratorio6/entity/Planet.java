package com.example.laboratorio6.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "planets")
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 255)
    private String nombre;

    @Column(name = "tipo_planeta", nullable = false, length = 100)
    private String tipoPlaneta;

    @Column(name = "habitable", nullable = false)
    private Boolean habitable;

    @Column(name = "gravedad_relativa", nullable = false)
    private Double gravedadRelativa;

    @Lob
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
}