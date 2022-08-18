package com.elca.internship.server.controllers;


import com.elca.internship.server.adapter.EmployeeAdapter;
import com.elca.internship.server.models.dto.EmployeeDto;
import com.elca.internship.server.models.entity.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    /*private final EmployeeService employeeService;

    @GetMapping()
    public List<Employee> getAllEmployees(){
        return employeeService.getAll();
    }

    @GetMapping("/visa-name")
    public List<String> getVisaAndNameOfAllEmployees(){
        return employeeService.getVisaAndNameOfAllEmployees();
    }*/
    private final EmployeeAdapter employeeAdapter;

    @GetMapping
    public List<EmployeeDto> getAllEmployees(){
        return employeeAdapter.getAllEmployees();
    }

    @GetMapping("/{projectId}")
    public List<EmployeeDto> getAllEmployeesByProjectId(@PathVariable("projectId") Long projectId) {
        return employeeAdapter.getAllEmployeesByProjectId(projectId);
    }

}
