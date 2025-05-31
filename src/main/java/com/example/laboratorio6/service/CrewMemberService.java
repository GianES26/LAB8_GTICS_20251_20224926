package com.example.laboratorio6.service;

import com.example.laboratorio6.entity.CrewMember;
import com.example.laboratorio6.repository.CrewMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrewMemberService {
    private final CrewMemberRepository crewMemberRepository;

    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public List<CrewMember> findAll() {
        return crewMemberRepository.findAll();
    }

    public Optional<CrewMember> findById(Long id) {
        return crewMemberRepository.findById(id);
    }

    public void save(CrewMember crewMember) {
        crewMemberRepository.save(crewMember);
    }

    public void deleteById(Long id) {
        crewMemberRepository.deleteById(id);
    }
}