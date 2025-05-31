package com.example.laboratorio6.controller;

import com.example.laboratorio6.service.CrewMemberService;
import com.example.laboratorio6.service.ExpeditionService;
import com.example.laboratorio6.service.PlanetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ExpeditionService expeditionService;
    private final PlanetService planetService;
    private final CrewMemberService crewMemberService;

    public HomeController(ExpeditionService expeditionService, PlanetService planetService, CrewMemberService crewMemberService) {
        this.expeditionService = expeditionService;
        this.planetService = planetService;
        this.crewMemberService = crewMemberService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("expediciones", expeditionService.findAll());
        model.addAttribute("planetas", planetService.findAll());
        model.addAttribute("miembrosTripulacion", crewMemberService.findAll());
        return "index";
    }
}