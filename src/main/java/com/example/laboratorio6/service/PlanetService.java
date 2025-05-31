package com.example.laboratorio6.service;

import com.example.laboratorio6.entity.Planet;
import com.example.laboratorio6.repository.PlanetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {
    private final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public List<Planet> findAll() {
        return planetRepository.findAll();
    }

    public Optional<Planet> findById(Long id) {
        return planetRepository.findById(id);
    }

    public Planet save(Planet planet) {
        return planetRepository.save(planet);
    }

    public void deleteById(Long id) {
        planetRepository.deleteById(id);
    }
}