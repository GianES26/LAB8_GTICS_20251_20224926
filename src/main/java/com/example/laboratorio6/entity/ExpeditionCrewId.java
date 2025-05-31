package com.example.laboratorio6.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ExpeditionCrewId implements Serializable {
    private Long expeditionId;
    private Long crewMemberId;
}