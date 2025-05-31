package com.example.laboratorio6.controller;

import com.example.laboratorio6.entity.CrewMember;
import com.example.laboratorio6.service.CrewMemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tripulacion")
public class CrewMemberController {
    private final CrewMemberService crewMemberService;

    public CrewMemberController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("miembrosTripulacion", crewMemberService.findAll());
        return "tripulacion/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("miembro", new CrewMember());
        return "tripulacion/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        CrewMember miembro = crewMemberService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Miembro de tripulación no encontrado"));
        model.addAttribute("miembro", miembro);
        return "tripulacion/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("miembro") CrewMember miembro, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "tripulacion/formulario";
        }
        try {
            crewMemberService.save(miembro);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "tripulacion/formulario";
        }
        return "redirect:/tripulacion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        crewMemberService.deleteById(id);
        return "redirect:/tripulacion";
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable Long id, Model model) {
        CrewMember miembro = crewMemberService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Miembro de tripulación no encontrado"));
        model.addAttribute("miembro", miembro);
        return "tripulacion/detalle";
    }
}