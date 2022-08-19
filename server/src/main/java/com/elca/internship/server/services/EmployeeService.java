package com.elca.internship.server.services;

import com.elca.internship.server.models.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployee();

    List<Employee> getAllEmployeeByProjectId(Long projectId);
}