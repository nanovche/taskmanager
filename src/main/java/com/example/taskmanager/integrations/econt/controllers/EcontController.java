package com.example.taskmanager.integrations.econt.controllers;

import com.example.taskmanager.integrations.econt.services.EcontApiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/econt")
public class EcontController {

    private final EcontApiService econtApiService;

    public EcontController(EcontApiService econtApiService) {
        this.econtApiService = econtApiService;
    }

    @GetMapping(path = "/offices")
    public ResponseEntity<Object> getOffices(){
        return econtApiService.getOffices();
    }
}