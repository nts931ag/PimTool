package com.elca.internship.server.controllers;


import com.elca.internship.server.models.entity.Employee;
import com.elca.internship.server.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping()
    public List<Employee> getAllEmployees(){
        return employeeService.getAll();
    }
}
