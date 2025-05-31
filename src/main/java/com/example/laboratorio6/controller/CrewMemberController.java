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
    public String formularioCrear(Model model) {
        model.addAttribute("miembroTripulacion", new CrewMember());
        return "tripulacion/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("miembroTripulacion") CrewMember miembroTripulacion, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "tripulacion/formulario";
        }
        crewMemberService.save(miembroTripulacion);
        return "redirect:/tripulacion";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("miembroTripulacion", crewMemberService.findById(id).orElseThrow(() -> new IllegalArgumentException("Miembro no encontrado")));
        return "tripulacion/detalle";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("miembroTripulacion", crewMemberService.findById(id).orElseThrow(() -> new IllegalArgumentException("Miembro no encontrado")));
        return "tripulacion/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        crewMemberService.deleteById(id);
        return "redirect:/tripulacion";
    }
}