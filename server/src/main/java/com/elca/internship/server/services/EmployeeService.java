package com.elca.internship.server.services;

import com.elca.internship.server.models.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Long> getIdsByListVisa(List<String> listVisa);

    List<Employee> getAll();

    List<String> getVisaAndNameOfAllEmployees();
}
