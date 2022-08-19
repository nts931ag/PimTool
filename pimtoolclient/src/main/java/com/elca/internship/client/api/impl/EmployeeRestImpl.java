package com.elca.internship.client.api.impl;

import com.elca.internship.client.api.EmployeeRest;
import com.elca.internship.client.models.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class EmployeeRestImpl implements EmployeeRest {

    public static final String URI_GET_ALL_EMPLOYEE = "http://localhost:8080/api/employees";
    public static final String URI_GET_ALL_EMPLOYEE_OF_CURRENT_PROJECT_BY_ID = "http://localhost:8080/api/employees/{projectId}";

    private final WebClient webClient;

    @Override
    public Flux<Employee> getAllEmployee() {
        return webClient.get().uri(URI_GET_ALL_EMPLOYEE).retrieve().bodyToFlux(Employee.class);
    }

    @Override
    public Flux<Employee> getAllEmployeeOfCurrentProject(Long currentProjectId) {
        return webClient.get().uri(URI_GET_ALL_EMPLOYEE_OF_CURRENT_PROJECT_BY_ID, currentProjectId).retrieve()
                .bodyToFlux(Employee.class);
    }
}
