package com.elca.internship.server.controllers;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.entity.ProjectEmployee;
import com.elca.internship.server.services.EmployeeService;
import com.elca.internship.server.services.ProjectEmployeeService;
import com.elca.internship.server.services.ProjectService;
import com.elca.internship.server.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectEmployeeService projectEmployeeService;
    private final EmployeeService employeeService;


    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity createNewProject(@RequestBody String jsonObject){
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), Project.class);
            var listEmployee = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            var listEmployeeId = employeeService.getIdsByListVisa(listEmployee);
            long newProjectId = projectService.createNewProject(project);
            projectEmployeeService.saveAllEmployeeToNewProject(newProjectId, listEmployeeId);

            return new ResponseEntity<Response>(HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("can't parse json to object");
            return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteProject(@PathVariable(value = "id") Long projectId){
        var project = projectService.getProject(projectId);
        if(project != null){
            projectEmployeeService.removeProjectEmployeeByProjectId(projectId);
            projectService.deleteProject(project);
            return new ResponseEntity<Response>(HttpStatus.OK);
        }else{
            return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public List<Project> getAllProject(){
        return projectService.getAllProject();
    }

    @GetMapping(value = "/search")
    public List<Project> searchProjectCriterialSpecified(@RequestParam(value = "proName") String proName, @RequestParam(value = "proStatus") String proStatus){

        return projectService.getProjectByCriterial(proName, proStatus);
    }
}
