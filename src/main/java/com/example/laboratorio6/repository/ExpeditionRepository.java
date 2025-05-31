package com.example.laboratorio6.repository;

import com.example.laboratorio6.entity.Expedition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpeditionRepository extends JpaRepository<Expedition, Long> {
}