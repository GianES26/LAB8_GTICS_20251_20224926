package com.example.laboratorio6.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "expeditions")
public class Expedition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre_mision", nullable = false, length = 255)
    private String nombreMision;

    @ManyToOne
    @JoinColumn(name = "planeta_destino_id")
    private Planet planetaDestino;

    @Column(name = "fecha_lanzamiento", nullable = false)
    private LocalDateTime fechaLanzamiento;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Lob
    @Column(name = "objetivos", columnDefinition = "TEXT")
    private String objetivos;

    @Lob
    @Column(name = "resultados", columnDefinition = "TEXT")
    private String resultados;

    @OneToMany(mappedBy = "expedition")
    private Set<ExpeditionCrew> expeditionCrew;
}