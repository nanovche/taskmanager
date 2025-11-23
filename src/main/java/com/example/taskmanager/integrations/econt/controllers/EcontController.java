package com.example.taskmanager.integrations.econt.controllers;

import com.example.taskmanager.dto.QuarterEcontApiResponseDto;
import com.example.taskmanager.dto.QuartersRequestDto;
import com.example.taskmanager.integrations.econt.services.EcontApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/econt")
public class EcontController {

    private final EcontApiService econtApiService;

    public EcontController(EcontApiService econtApiService) {
        this.econtApiService = econtApiService;
    }

    @PostMapping(path = "/quarters")
    public String getQuarters(@RequestParam @Validated Long cityID, Model model){
        QuarterEcontApiResponseDto quartersForCity = econtApiService.getQuartersForCity(cityID);
        model.addAttribute("quarters", quartersForCity.getQuarterList());
        return "econt/econt-page";
    }

    @GetMapping(path = "/select-quarter")
    public String render(){
        return "econt/select-quarter";
    }
}