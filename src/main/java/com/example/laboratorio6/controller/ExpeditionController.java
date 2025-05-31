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
    public String formularioCrear(Model model) {
        model.addAttribute("expedicion", new Expedition());
        model.addAttribute("planetas", planetService.findAll());
        model.addAttribute("miembrosTripulacion", crewMemberService.findAll());
        return "expediciones/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute Expedition expedicion, @RequestParam(value = "idsMiembros", required = false) List<Long> idsMiembros, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("planetas", planetService.findAll());
            model.addAttribute("miembrosTripulacion", crewMemberService.findAll());
            return "expediciones/formulario";
        }
        try {
            expeditionService.save(expedicion, idsMiembros);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("planetas", planetService.findAll());
            model.addAttribute("miembrosTripulacion", crewMemberService.findAll());
            return "expediciones/formulario";
        }
        return "redirect:/expediciones";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("expedicion", expeditionService.findById(id).orElseThrow(() -> new IllegalArgumentException("Expedición no encontrada")));
        return "expediciones/detalle";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("expedicion", expeditionService.findById(id).orElseThrow(() -> new IllegalArgumentException("Expedición no encontrada")));
        model.addAttribute("planetas", planetService.findAll());
        model.addAttribute("miembrosTripulacion", crewMemberService.findAll());
        return "expediciones/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        expeditionService.deleteById(id);
        return "redirect:/expediciones";
    }

    @PostMapping("/actualizar-estado/{id}")
    public String actualizarEstado(@PathVariable Long id, @RequestParam String estado, @RequestParam(required = false) String resultados, Model model) {
        try {
            expeditionService.updateStatus(id, estado, resultados);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("expedicion", expeditionService.findById(id).orElseThrow(() -> new IllegalArgumentException("Expedición no encontrada")));
            return "expediciones/detalle";
        }
        return "redirect:/expediciones";
    }
}