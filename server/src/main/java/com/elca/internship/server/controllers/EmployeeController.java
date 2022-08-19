package com.elca.internship.server.controllers;


import com.elca.internship.server.mappers.EmployeeMapperCustom;
import com.elca.internship.server.models.dto.EmployeeDto;
import com.elca.internship.server.services.news.EmployeeService;
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
    private final EmployeeService employeeService;
    private final EmployeeMapperCustom employeeMapperCustom;

    @GetMapping
    public List<EmployeeDto> getAllEmployees(){
        var listEmployee = employeeService.getAllEmployee();
        return employeeMapperCustom.listEntityToListDto(listEmployee);
    }

    @GetMapping("/{projectId}")
    public List<EmployeeDto> getAllEmployeesByProjectId(@PathVariable("projectId") Long projectId) {
        return employeeMapperCustom.listEntityToListDto(employeeService.getAllEmployeeByProjectId(projectId));
    }

}
