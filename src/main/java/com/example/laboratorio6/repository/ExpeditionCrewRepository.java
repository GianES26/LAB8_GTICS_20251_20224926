package com.example.laboratorio6.repository;

import com.example.laboratorio6.entity.ExpeditionCrew;
import com.example.laboratorio6.entity.ExpeditionCrewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpeditionCrewRepository extends JpaRepository<ExpeditionCrew, ExpeditionCrewId> {
    @Query("SELECT ec FROM ExpeditionCrew ec WHERE ec.expedition.id = :expeditionId")
    List<ExpeditionCrew> findByExpeditionId(Long expeditionId);

    @Query("SELECT ec FROM ExpeditionCrew ec WHERE ec.crewMember.id = :crewMemberId AND ec.expedition.estado IN ('Planificada', 'En Curso')")
    List<ExpeditionCrew> findActiveExpeditionsByCrewMemberId(Long crewMemberId);
}