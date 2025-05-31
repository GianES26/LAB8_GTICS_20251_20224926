package com.example.laboratorio6.repository;

import com.example.laboratorio6.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
    Planet findByNombre(String nombre);
}