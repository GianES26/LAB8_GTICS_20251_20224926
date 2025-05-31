package com.example.laboratorio6.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "crew_members")
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 255)
    private String nombreCompleto;

    @Column(name = "especialidad", nullable = false, length = 100)
    private String especialidad;

    @Column(name = "rango", length = 100)
    private String rango;

    @Column(name = "fecha_contratacion", nullable = false)
    private LocalDate fechaContratacion;
}