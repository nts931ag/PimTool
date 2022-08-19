package com.elca.internship.client.api;

import com.elca.internship.client.models.Employee;
import reactor.core.publisher.Flux;

public interface EmployeeRest {
    Flux<Employee> getAllEmployee();

    Flux<Employee> getAllEmployeeOfCurrentProject(Long currentProjectId);
}
