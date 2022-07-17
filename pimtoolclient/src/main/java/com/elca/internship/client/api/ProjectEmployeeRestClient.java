package com.elca.internship.client.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@RequiredArgsConstructor
public class ProjectEmployeeRestClient {
    public static final String URI_GET_ALL_EMPLOYEE_OF_PROJECT_ID = BASE_URI + "/api/projects-employees/{id}";

    private final WebClient webClient;


}
