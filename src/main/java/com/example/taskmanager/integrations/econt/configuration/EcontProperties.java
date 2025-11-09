package com.example.taskmanager.integrations.econt.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "econt.api")
public class EcontProps {

    private String username;
    private String password;
    private String url;
}