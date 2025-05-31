package com.example.laboratorio6.controller;

import com.example.laboratorio6.entity.Planet;
import com.example.laboratorio6.service.PlanetService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/planetas")
public class PlanetController {
    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("planetas", planetService.findAll());
        return "planetas/lista";
    }

    @GetMapping("/nuevo")
    public String formularioCrear(Model model) {
        model.addAttribute("planeta", new Planet());
        return "planetas/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute Planet planeta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "planetas/formulario";
        }
        planetService.save(planeta);
        return "redirect:/planetas";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("planeta", planetService.findById(id).orElseThrow(() -> new IllegalArgumentException("Planeta no encontrado")));
        return "planetas/detalle";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("planeta", planetService.findById(id).orElseThrow(() -> new IllegalArgumentException("Planeta no encontrado")));
        return "planetas/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        planetService.deleteById(id);
        return "redirect:/planetas";
    }
}