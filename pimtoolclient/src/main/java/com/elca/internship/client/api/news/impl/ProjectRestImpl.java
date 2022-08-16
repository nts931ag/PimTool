package com.elca.internship.client.api.news.impl;

import com.elca.internship.client.api.news.ProjectRest;
import com.elca.internship.client.controllers.DashboardController;
import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.models.entity.Project;
import javafx.application.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectRestImpl implements ProjectRest {
    private final WebClient webClient;

    public static final String URI_GET_ALL_PROJECT = "http://localhost:8080/api/projects";
    public static final String URI_DELETE_PROJECT_BY_ID = "http://localhost:8080/api/projects/{id}";
    public static final String URI_GET_PROJECT_BY_PROJECT_NUMBER = "http://localhost:8080/api/projects/{projectNumber}";
    private static final String URI_DELETE_PROJECTS_BY_IDS = "http://localhost:8080/api/projects/delete";
    private static final String URI_CREATE_NEW_PROJECT = "http://localhost:8080/api/projects/save";
    private static final String URI_UPDATE_PROJECT = "http://localhost:8080/api/projects/update";


    @Override
    public Flux<Project> getAllProject() {
        return webClient.get().uri(URI_GET_ALL_PROJECT)
                .retrieve()
                .bodyToFlux(Project.class)
                .doOnError(
                throwable -> Platform.runLater(
                        () -> DashboardController.navigationHandler
                                .handleNavigateToErrorPage(I18nKey.APPLICATION_ERROR_CONNECTION)
                )
        );
    }

    @Override
    public void deleteProjectById(Long id) {
        webClient.delete().uri(URI_DELETE_PROJECT_BY_ID, id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(
                        throwable -> Platform.runLater(
                                () -> DashboardController.navigationHandler
                                        .handleNavigateToErrorPage(I18nKey.APPLICATION_ERROR_CONNECTION)
                        )
                )
                .block();
    }

    @Override
    public void deleteProjectByIds(List<Long> listIdDelete) {
        var uri = UriComponentsBuilder.fromUriString(URI_DELETE_PROJECTS_BY_IDS)
                .queryParam("Ids", listIdDelete)
                .build().toUriString();
        webClient.delete()
                .uri(uri)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(
                        throwable -> Platform.runLater(
                                () -> DashboardController.navigationHandler
                                        .handleNavigateToErrorPage(I18nKey.APPLICATION_ERROR_CONNECTION)
                        )
                )
                .block();
    }

    @Override
    public Mono<Project> getProjectByProjectNumber(Integer projectNumber) {
        return webClient.get().uri(URI_GET_PROJECT_BY_PROJECT_NUMBER, projectNumber)
                .retrieve()
                .bodyToMono(Project.class)
                .doOnError(
                        throwable -> Platform.runLater(
                                () -> DashboardController.navigationHandler
                                        .handleNavigateToErrorPage(I18nKey.APPLICATION_ERROR_CONNECTION)
                        )
                );
    }

    //    public void createNewProjectTest(String jsonObject) {
//        webClient.post().uri(URI_SAVE_NEW_PROJECT)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(jsonObject))
//                .retrieve()
////                .onStatus(HttpStatus::is4xxClientError, clientResponse -> handle4xxError(clientResponse))
////                .onStatus(HttpStatus::is5xxServerError, clientResponse -> handle5xxError(clientResponse))
//                .bodyToMono(Void.class)
//                .onErrorStop()
//                .block();
//    }

    @Override
    public void createNewProject(String jsonObject) {
        webClient.post().uri(URI_CREATE_NEW_PROJECT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonObject))
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorStop()
                .block();
    }

    @Override
    public void updateProject(String jsonObject) {
        webClient.put().uri(URI_UPDATE_PROJECT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonObject))
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorStop()
                .block();
    }
}
