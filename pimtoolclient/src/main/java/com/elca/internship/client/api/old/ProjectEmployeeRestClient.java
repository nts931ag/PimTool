//package com.elca.internship.client.api.old;
//
//import javafx.collections.ObservableList;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//import java.util.List;
//
//import static com.elca.internship.client.config.connection.Rest.BASE_URI;
//
//@Component
//@RequiredArgsConstructor
//public class ProjectEmployeeRestClient {
//    public static final String URI_GET_ALL_EMPLOYEE_OF_PROJECT_ID = BASE_URI + "/api/project-employee/{projectId}";
//    public static final String URI_GET_ALL_VISA_NAME_OF_PROJECT_ID = BASE_URI + "/api/project-employee/visa-name/{projectId}";
//    private final WebClient webClient;
//
//
//    public List<String> getAllEmployeeVisaByProjectId(Long projectId) {
//        /*var uri = UriComponentsBuilder.fromUriString(URI_GET_ALL_EMPLOYEE_OF_PROJECT_ID)
//                        .queryParam("projectId", projectId).build().toUriString();
//        return webClient.get().uri(uri)
//                .retrieve()
//                .bodyToFlux(String.class)
//                .collectList()
//                .block();*/
//        return webClient.get().uri(URI_GET_ALL_EMPLOYEE_OF_PROJECT_ID,projectId)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
//                })
//                .block();
//
//    }
//
//    public List<String> getAllVisaAndNameOfEmployeeByProjectId(Long id) {
//        return webClient.get().uri(URI_GET_ALL_VISA_NAME_OF_PROJECT_ID,id)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
//                })
//                .block();
//    }
//}
