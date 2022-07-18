package com.elca.internship.client.api;

import com.elca.internship.client.models.entity.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@RequiredArgsConstructor
public class ProjectRestClient {
    public static final String URI_GET_ALL_PROJECT = BASE_URI + "/api/projects";
    public static final String URI_GET_ALL_PROJECT_NUMBER = BASE_URI + "/api/projects/numbers";
    public static final String URI_SAVE_NEW_PROJECT = BASE_URI + "/api/project";
    public static final String URI_GET_ALL_PROJECT_CRITERIA_SPECIFIED = BASE_URI + "/api/projects/search";
    public static final String URI_DELETE_PROJECT_BY_ID = BASE_URI + "/api/projects/{id}";
    public static final String URI_GET_PROJECT_BY_ID = BASE_URI + "/api/projects/{id}";
    public static final String URI_UPDATE_PROJECT_CHANGE = BASE_URI + "/api/projects/update";
    private final WebClient webClient;

    public List<Project> getAllProjects() {
        return webClient.get().uri(URI_GET_ALL_PROJECT)
                .retrieve()
                .bodyToFlux(Project.class)
                .collectList()
                .block();
    }

    public List<Integer> getAllProjectNumbers() {
        return webClient.get().uri(URI_GET_ALL_PROJECT_NUMBER)
                .retrieve()
                .bodyToFlux(Integer.class)
                .collectList()
                .block();
    }


    public String deleteById(Long projectId) {
        return webClient.delete().uri(URI_DELETE_PROJECT_BY_ID, projectId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public ResponseEntity updateProject(String jsonObject) {
        return webClient.put().uri(URI_UPDATE_PROJECT_CHANGE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonObject))
                .retrieve()
                .toBodilessEntity().block();
    }

    public List<Project> getAllProjectsByCriteriaSpecified(String tfSearchValue, String cbStatusValue) {
        var uri = UriComponentsBuilder.fromUriString(URI_GET_ALL_PROJECT_CRITERIA_SPECIFIED)
                .queryParam("proName", tfSearchValue)
                .queryParam("proStatus", cbStatusValue)
                .build().toUriString();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Project.class)
                .collectList()
                .block();
    }
}