package com.elca.internship.client.api.news;

import com.elca.internship.client.models.entity.Employee;
import reactor.core.publisher.Flux;

import java.util.List;

public interface EmployeeRest {
    Flux<Employee> getAllEmployee();

    Flux<Employee> getAllEmployeeOfCurrentProject(Long currentProjectId);
}
