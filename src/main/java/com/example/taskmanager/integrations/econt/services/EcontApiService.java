package com.example.taskmanager.integrations.econt.services;

import com.example.taskmanager.exception.ExternalCommunicationException;
import com.example.taskmanager.exception.ExternalServiceException;
import com.example.taskmanager.exception.ExternalServiceUnavailableException;
import com.example.taskmanager.integrations.econt.configuration.EcontProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;


@Service
public class EcontApiService {

    private final RestTemplate restTemplate;
    private final EcontProperties econtProperties;

    public EcontApiService(RestTemplate restTemplate, EcontProperties econtProperties) {
        this.restTemplate = restTemplate;
        this.econtProperties = econtProperties;
    }

    @Retryable(
            retryFor = {HttpServerErrorException.class, ResourceAccessException.class},
            backoff = @Backoff(delay = 2000)
    )
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
        } catch (HttpClientErrorException e) {
            // 400-level errors (bad request, unauthorized, not found, etc.)
            System.err.println("Client error (4xx): " + e.getStatusCode() + " - " + e.getStatusText() + " - " + e.getResponseBodyAsString());
            // Translate to your domain exception
            throw new ExternalServiceException("Invalid request to external API", e);
        } catch (HttpServerErrorException e) {
            // 500-level errors (server unavailable, internal server error)
            System.err.println("Server error (5xx): " + e.getStatusCode() + " - " + e.getStatusText() + " - " + e.getResponseBodyAsString());
            // Optionally retry or wrap
            throw new ExternalServiceUnavailableException("External API server error", e);
        } catch (ResourceAccessException e) {
            // Network issues, timeouts
            System.err.println("Network error: " + e.getMessage());
            throw new ExternalCommunicationException("Cannot reach external API", e);
        }catch (RestClientException e) {
            // Fallback for other unexpected errors
            System.err.println("REST client error: " + e.getMessage());
            throw new ExternalServiceException("Unexpected REST client error", e);
        }
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