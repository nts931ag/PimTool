package com.elca.internship.client.api;

import com.elca.internship.client.models.entity.Employee;
import com.elca.internship.client.models.entity.Project;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;
@Component
@RequiredArgsConstructor
public class EmployeeRestClient {
    public static final String URI_GET_ALL_EMPLOYEE = BASE_URI + "/api/employees";
    public static final String URI_GET_VISA_NAME_OF_ALL_EMPLOYEE = BASE_URI + "/api/employees/visa-name";

    private final WebClient webClient;
    public List<Employee> getAllEmployees(){
        return webClient.get().uri(URI_GET_ALL_EMPLOYEE)
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList()
                .block();
    }


    public List<String> getVisaAndNameOfAllEmployees() {
        return webClient.get().uri(URI_GET_VISA_NAME_OF_ALL_EMPLOYEE)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }
}
