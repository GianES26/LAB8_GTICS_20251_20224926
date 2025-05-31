package com.example.laboratorio6.controller;

import com.example.laboratorio6.entity.Expedition;
import com.example.laboratorio6.service.CrewMemberService;
import com.example.laboratorio6.service.ExpeditionService;
import com.example.laboratorio6.service.PlanetService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/expediciones")
public class ExpeditionController {
    private final ExpeditionService expeditionService;
    private final PlanetService planetService;
    private final CrewMemberService crewMemberService;

    public ExpeditionController(ExpeditionService expeditionService, PlanetService planetService, CrewMemberService crewMemberService) {
        this.expeditionService = expeditionService;
        this.planetService = planetService;
        this.crewMemberService = crewMemberService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("expediciones", expeditionService.findAll());
        return "expediciones/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("expedicion", new Expedition());
        model.addAttribute("planetas", planetService.findAll());
        model.addAttribute("miembrosTripulacion", crewMemberService.findAll());
        return "expediciones/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Expedition expedicion = expeditionService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expedición no encontrada"));
        model.addAttribute("expedicion", expedicion);
        model.addAttribute("planetas", planetService.findAll());
        model.addAttribute("miembrosTripulacion", crewMemberService.findAll());
        return "expediciones/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("expedicion") Expedition expedicion, BindingResult result,
                          @RequestParam(value = "idsMiembros", required = false) List<Long> crewMemberIds, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("planetas", planetService.findAll());
            model.addAttribute("miembrosTripulacion", crewMemberService.findAll());
            return "expediciones/formulario";
        }
        try {
            expeditionService.save(expedicion, crewMemberIds);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("planetas", planetService.findAll());
            model.addAttribute("miembrosTripulacion", crewMemberService.findAll());
            return "expediciones/formulario";
        }
        return "redirect:/expediciones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        expeditionService.deleteById(id);
        return "redirect:/expediciones";
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Expedition expedicion = expeditionService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expedición no encontrada"));
        model.addAttribute("expedicion", expedicion);
        return "expediciones/detalle";
    }
}