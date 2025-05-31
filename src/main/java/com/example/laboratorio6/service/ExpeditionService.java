package com.example.laboratorio6.service;

import com.example.laboratorio6.entity.CrewMember;
import com.example.laboratorio6.entity.Expedition;
import com.example.laboratorio6.entity.ExpeditionCrew;
import com.example.laboratorio6.entity.ExpeditionCrewId;
import com.example.laboratorio6.repository.ExpeditionCrewRepository;
import com.example.laboratorio6.repository.ExpeditionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExpeditionService {
    private final ExpeditionRepository expeditionRepository;
    private final ExpeditionCrewRepository expeditionCrewRepository;
    private final CrewMemberService crewMemberService;

    public ExpeditionService(ExpeditionRepository expeditionRepository, ExpeditionCrewRepository expeditionCrewRepository, CrewMemberService crewMemberService) {
        this.expeditionRepository = expeditionRepository;
        this.expeditionCrewRepository = expeditionCrewRepository;
        this.crewMemberService = crewMemberService;
    }

    public List<Expedition> findAll() {
        return expeditionRepository.findAll();
    }

    public Optional<Expedition> findById(Long id) {
        return expeditionRepository.findById(id);
    }

    @Transactional
    public Expedition save(Expedition expedition, List<Long> crewMemberIds) {
        // Validar asignación de tripulación
        if (!validateCrewAssignments(expedition, crewMemberIds)) {
            throw new IllegalArgumentException("La expedición debe tener al menos un Piloto y un Científico.");
        }
        // Verificar disponibilidad de miembros
        for (Long crewMemberId : crewMemberIds) {
            if (!isCrewMemberAvailable(crewMemberId)) {
                throw new IllegalArgumentException("El miembro de la tripulación con ID " + crewMemberId + " ya está asignado a una expedición activa.");
            }
        }
        // Guardar expedición
        Expedition savedExpedition = expeditionRepository.save(expedition);
        // Guardar asignaciones de tripulación
        if (crewMemberIds != null) {
            for (Long crewMemberId : crewMemberIds) {
                ExpeditionCrew expeditionCrew = new ExpeditionCrew();
                ExpeditionCrewId id = new ExpeditionCrewId();
                id.setExpeditionId(savedExpedition.getId());
                id.setCrewMemberId(crewMemberId);
                expeditionCrew.setId(id);
                expeditionCrew.setExpedition(savedExpedition);
                CrewMember crewMember = new CrewMember();
                crewMember.setId(crewMemberId);
                expeditionCrew.setCrewMember(crewMember);
                expeditionCrewRepository.save(expeditionCrew);
            }
        }
        return savedExpedition;
    }

    @Transactional
    public void deleteById(Long id) {
        expeditionCrewRepository.findByExpeditionId(id).forEach(expeditionCrewRepository::delete);
        expeditionRepository.deleteById(id);
    }

    @Transactional
    public Expedition updateStatus(Long id, String newStatus, String resultados) {
        Expedition expedition = expeditionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expedición no encontrada"));

        if ("En Curso".equals(newStatus) && !"Planificada".equals(expedition.getEstado())) {
            throw new IllegalArgumentException("Solo se puede cambiar a En Curso desde Planificada.");
        }
        if ("En Curso".equals(newStatus) && !validateCrewForLaunch(expedition)) {
            throw new IllegalArgumentException("La expedición debe tener al menos un Piloto y un Científico para lanzarse.");
        }
        if ("Completada".equals(newStatus)) {
            expedition.setResultados(resultados);
        }
        if ("Cancelada".equals(newStatus)) {
            expeditionCrewRepository.findByExpeditionId(id).forEach(expeditionCrewRepository::delete);
        }

        expedition.setEstado(newStatus);
        return expeditionRepository.save(expedition);
    }

    private boolean validateCrewAssignments(Expedition expedition, List<Long> crewMemberIds) {
        if (crewMemberIds == null || crewMemberIds.isEmpty()) {
            return false;
        }
        List<ExpeditionCrew> existingCrew = expeditionCrewRepository.findByExpeditionId(expedition.getId());
        Set<String> specialties = existingCrew.stream()
                .map(ec -> ec.getCrewMember().getEspecialidad())
                .collect(Collectors.toSet());
        if (crewMemberIds != null) {
            for (Long crewMemberId : crewMemberIds) {
                Optional<CrewMember> crewMember = crewMemberService.findById(crewMemberId);
                if (crewMember.isPresent()) {
                    specialties.add(crewMember.get().getEspecialidad());
                }
            }
        }
        return specialties.contains("Piloto") && specialties.contains("Científico");
    }

    private boolean validateCrewForLaunch(Expedition expedition) {
        List<ExpeditionCrew> crew = expeditionCrewRepository.findByExpeditionId(expedition.getId());
        boolean hasPilot = false;
        boolean hasScientist = false;
        for (ExpeditionCrew ec : crew) {
            String specialty = ec.getCrewMember().getEspecialidad();
            if ("Piloto".equals(specialty)) hasPilot = true;
            if ("Científico".equals(specialty)) hasScientist = true;
        }
        return hasPilot && hasScientist;
    }

    private boolean isCrewMemberAvailable(Long crewMemberId) {
        return expeditionCrewRepository.findActiveExpeditionsByCrewMemberId(crewMemberId).isEmpty();
    }
}