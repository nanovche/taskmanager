package com.example.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class ProjectConfig {

    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}