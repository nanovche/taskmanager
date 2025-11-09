package com.example.taskmanager.integrations.econt.services;

import com.example.taskmanager.integrations.econt.configuration.EcontProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
public class EcontApiService {

    private final RestTemplate restTemplate;
    private final EcontProperties econtProperties;

    public EcontApiService(RestTemplate restTemplate, EcontProperties econtProperties) {
        this.restTemplate = restTemplate;
        this.econtProperties = econtProperties;
    }

    public ResponseEntity<Object> getOffices(){

        String url = String.format("%s/%s.getOffices.%s",
                econtProperties.getBaseUrl(),
                econtProperties.getServices().getNomenclatures(),
                econtProperties.getFormat());
        try {
            return restTemplate.getForEntity(
                    url,
                    Object.class,
                    prepareHeaders());
        } catch (RestClientException e) {
            System.err.println("getOffices econt fail");
        }
        return null;
    }

    private HttpHeaders prepareHeaders(){

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(econtProperties.getUsername(), econtProperties.getPassword());
        MediaType mediaType = econtProperties.getFormat().equalsIgnoreCase("json") ?
                MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        headers.setContentType(mediaType);
        return headers;
    }
}