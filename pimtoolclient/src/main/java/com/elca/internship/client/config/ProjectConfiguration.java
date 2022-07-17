package com.elca.internship.client.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProjectConfiguration {

    @Bean
    public RestTemplate restTemplate(){
        var restTemplateBuilder = new RestTemplateBuilder();
        return  restTemplateBuilder.build();
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .build();
    }

    
}
