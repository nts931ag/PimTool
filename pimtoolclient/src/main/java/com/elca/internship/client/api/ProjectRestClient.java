package com.elca.internship.client.api;

import com.elca.internship.client.controllers.DashboardController;
import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.Response;
import com.elca.internship.client.exception.ErrorResponse;
import com.elca.internship.client.exception.ProjectException;
import javafx.application.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

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
    private static final String URI_GET_PROJECT_NUMBER = BASE_URI + "/api/projects/{proNum}";

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

    public Response deleteById(Long projectId) {
        return webClient.delete().uri(URI_DELETE_PROJECT_BY_ID, projectId)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnError(
                        throwable -> Platform.runLater(
                                () -> DashboardController.navigationHandler
                                        .handleNavigateToErrorPage(I18nKey.APPLICATION_ERROR_CONNECTION)
                        )
                )
                .onErrorReturn(new Response(-1,"FAIL"))
                .block();
    }

    public Response deleteByIds(List<Long> listIdDelete) {
        var uri = UriComponentsBuilder.fromUriString(URI_DELETE_PROJECTS_BY_IDS)
                .queryParam("Ids", listIdDelete)
                .build().toUriString();
        return webClient.delete()
                .uri(uri)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnError(
                        throwable -> Platform.runLater(
                                () -> DashboardController.navigationHandler
                                        .handleNavigateToErrorPage(I18nKey.APPLICATION_ERROR_CONNECTION)
                        )
                )
                .onErrorReturn(new Response(-1,"FAIL"))
                .block();
    }

    public Mono<Long> getProjectNumber(Long projectNumber) {
        return webClient.get().uri(URI_GET_PROJECT_NUMBER, projectNumber)
                .retrieve()
                .bodyToMono(Long.class)
                .doOnError(
                        throwable -> Platform.runLater(
                                () -> DashboardController.navigationHandler
                                        .handleNavigateToErrorPage(I18nKey.APPLICATION_ERROR_CONNECTION)
                        )
                )
                .onErrorReturn(0L);
    }

    private static String URI_TEST = "http://localhost:8080/api/projects/test/search";

    public List<Project> getProjectsByCriteriaSpecifiedWithPagination(String tfSearchValue, String cbStatusValue, int pageIndex, int itemPerPage) {
        var uri = UriComponentsBuilder.fromUriString(URI_TEST)
                .queryParam("proCriteria", tfSearchValue)
                .queryParam("proStatus", cbStatusValue)
                .queryParam("pageNumber", pageIndex)
                .queryParam("pageSize", itemPerPage)
                .build().toUriString();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Project.class)
                .collectList()
                .doOnError(
                        throwable -> Platform.runLater(
                                () -> DashboardController.navigationHandler
                                        .handleNavigateToErrorPage(I18nKey.APPLICATION_ERROR_CONNECTION)
                        )
                )
                .onErrorReturn(List.of())
                .block();
    }


    public void createNewProjectTest(String jsonObject) {
        webClient.post().uri(URI_SAVE_NEW_PROJECT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonObject))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> handle4xxError(clientResponse))
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> handle5xxError(clientResponse))
                .bodyToMono(Void.class)
                .onErrorStop()
                .block();
    }



    public void updateNewProjectTest(String jsonObject) {
        webClient.put().uri(URI_UPDATE_PROJECT_CHANGE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonObject))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> handle4xxError(clientResponse))
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> handle5xxError(clientResponse))
                .bodyToMono(Void.class)
                .onErrorStop()
                .block();
    }

    private Mono<? extends Throwable> handle5xxError(ClientResponse clientResponse) {
        Mono<ErrorResponse> errorMessage = clientResponse.bodyToMono(ErrorResponse.class);
        return errorMessage.flatMap(
                message -> Mono.error(new ProjectException(message.getStatusMsg(), message.getI18nKey(), message.getI18nValue()))
        );
    }

    private Mono<? extends Throwable> handle4xxError(ClientResponse clientResponse) {
        Mono<ErrorResponse> errorMessage = clientResponse.bodyToMono(ErrorResponse.class);
        return errorMessage.flatMap(
                message -> Mono.error(new ProjectException(message.getStatusMsg(), message.getI18nKey(), message.getI18nValue()))
        );
    }

    private final String URI_GET_NUMBER_OF_RESULT_SEARCH= BASE_URI + "/api/projects/search/size";

    public int getNumberOfResultSearch(String tfSearch, String status) {
        var uri = UriComponentsBuilder.fromUriString(URI_GET_NUMBER_OF_RESULT_SEARCH)
                .queryParam("proCriteria", tfSearch)
                .queryParam("proStatus", status)
                .build().toUriString();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Integer.class)
                .doOnError(
                        throwable -> Platform.runLater(
                                () -> DashboardController.navigationHandler
                                        .handleNavigateToErrorPage(I18nKey.APPLICATION_ERROR_CONNECTION)
                        )
                )
                .onErrorReturn(0)
                .block();
    }
}
