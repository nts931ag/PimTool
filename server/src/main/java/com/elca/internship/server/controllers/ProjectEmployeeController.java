package com.elca.internship.server.controllers;


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
@RequestMapping("/api/project-employee/visa-name")
public class ProjectEmployeeController {
//    private final ProjectEmployeeService projectEmployeeService;
//
//    @GetMapping("/{projectId}")
//    public List<String> getAllEmployeesByProjectId(@PathVariable("projectId") Long projectId){
//        return projectEmployeeService.getAllVisaAndNameOfEmployeeByProjectId(projectId);
//
//    }
}
