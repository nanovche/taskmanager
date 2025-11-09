package com.example.taskmanager.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EcontApiService {

    private final RestTemplate restTemplate;

    public EcontApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getShip(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
    }
}