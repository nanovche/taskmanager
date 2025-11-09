package com.example.taskmanager.integrations.econt.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "econt.api")
@Getter
@Setter
public class EcontProperties {

    private String baseUrl;
    private String username;
    private String password;
    private String format;
    private Services services;

    @Getter
    @Setter
    public static class Services {
        private String nomenclatures;
    }
}