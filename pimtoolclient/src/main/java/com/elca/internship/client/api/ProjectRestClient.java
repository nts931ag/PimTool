package com.elca.internship.client.api;

import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@RequiredArgsConstructor
public class ProjectRestClient {
    public static final String URI_GET_ALL_PROJECT = BASE_URI + "/api/projects";
    public static final String URI_GET_ALL_PROJECT_NUMBER = BASE_URI + "/api/projects/numbers";
    public static final String URI_SAVE_NEW_PROJECT = BASE_URI + "/api/projects/save";
    public static final String URI_GET_ALL_PROJECT_CRITERIA_SPECIFIED = BASE_URI + "/api/projects/search";
    public static final String URI_DELETE_PROJECT_BY_ID = BASE_URI + "/api/projects/{id}";
    public static final String URI_GET_PROJECT_BY_ID = BASE_URI + "/api/projects/{id}";
    public static final String URI_UPDATE_PROJECT_CHANGE = BASE_URI + "/api/projects/update";
    private static final String URI_DELETE_PROJECTS_BY_IDS = BASE_URI + "/api/projects/delete";
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


    public Mono<Response> deleteById(Long projectId) {
        return webClient.delete().uri(URI_DELETE_PROJECT_BY_ID, projectId)
                .retrieve()
                .bodyToMono(Response.class);

    }

    public List<Project> getAllProjectsByCriteriaSpecified(String tfSearchValue, String cbStatusValue) {
        var uri = UriComponentsBuilder.fromUriString(URI_GET_ALL_PROJECT_CRITERIA_SPECIFIED)
                .queryParam("proCriteria", tfSearchValue)
                .queryParam("proStatus", cbStatusValue)
                .build().toUriString();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Project.class)
                .collectList()
                .block();
    }

    public Mono<Response> updateProject(String jsonObject) {

        return webClient.put().uri(URI_UPDATE_PROJECT_CHANGE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonObject))
                .retrieve()
                .bodyToMono(Response.class);
    }

    public Mono<Response> saveNewProject(String jsonObject) {

        return webClient.post().uri(URI_SAVE_NEW_PROJECT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonObject))
                .retrieve()
                .bodyToMono(Response.class);

    }

    public Mono<Response> deleteByIds(List<Long> listIdDelete) {
        var uri = UriComponentsBuilder.fromUriString(URI_DELETE_PROJECTS_BY_IDS)
                .queryParam("Ids", listIdDelete)
                .build().toUriString();
        return webClient.delete()
                .uri(uri)
                .retrieve()
                .bodyToMono(Response.class);
    }
}
