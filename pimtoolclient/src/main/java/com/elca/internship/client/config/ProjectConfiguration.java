package com.elca.internship.client.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProjectConfiguration {

    @Bean
    RestTemplate restTemplate(){
        var restTemplateBuilder = new RestTemplateBuilder();
        return  restTemplateBuilder.build();
    }

    
}
