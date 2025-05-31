package com.example.laboratorio6.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "expedition_crew")
public class ExpeditionCrew {
    @EmbeddedId
    private ExpeditionCrewId id;

    @ManyToOne
    @MapsId("expeditionId")
    @JoinColumn(name = "expedition_id")
    private Expedition expedition;

    @ManyToOne
    @MapsId("crewMemberId")
    @JoinColumn(name = "crew_member_id")
    private CrewMember crewMember;
}