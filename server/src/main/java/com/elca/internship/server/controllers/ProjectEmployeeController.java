package com.elca.internship.server.controllers;


import com.elca.internship.server.services.EmployeeService;
import com.elca.internship.server.services.ProjectEmployeeService;
import com.elca.internship.server.services.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project-employee")
public class ProjectEmployeeController {
    private final ProjectEmployeeService projectEmployeeService;

    @GetMapping("/{projectId}")
    public List<String> getAllEmployeesByProjectId(@PathVariable("projectId") Long projectId){
        var result = projectEmployeeService.getAllEmployeeVisasByProjectId(projectId);
        System.out.println(result.size());
        return result;
//        return projectEmployeeService.getAllEmployeeVisasByProjectId(projectId);

    }
}
