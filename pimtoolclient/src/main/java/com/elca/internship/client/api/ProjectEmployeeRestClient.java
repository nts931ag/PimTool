package com.elca.internship.client.api;

import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@RequiredArgsConstructor
public class ProjectEmployeeRestClient {
    public static final String URI_GET_ALL_EMPLOYEE_OF_PROJECT_ID = BASE_URI + "/api/project-employee/{projectId}";

    private final WebClient webClient;


    public List<String> getAllEmployeeVisaByProjectId(Long projectId) {
        /*var uri = UriComponentsBuilder.fromUriString(URI_GET_ALL_EMPLOYEE_OF_PROJECT_ID)
                        .queryParam("projectId", projectId).build().toUriString();
        return webClient.get().uri(uri)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .block();*/
        return webClient.get().uri(URI_GET_ALL_EMPLOYEE_OF_PROJECT_ID,projectId)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .block();

    }
}
