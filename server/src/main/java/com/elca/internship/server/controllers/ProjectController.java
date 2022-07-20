package com.elca.internship.server.controllers;

import com.elca.internship.server.models.entity.Group;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.entity.ProjectEmployee;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistsException;
import com.elca.internship.server.services.EmployeeService;
import com.elca.internship.server.services.GroupService;
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

    private final GroupService groupService;

    @GetMapping()
    public List<Project> getAllProject(){
        return projectService.getAllProject();
    }

    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<Response> createNewProject(@RequestBody String jsonObject){
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), Project.class);
            var listEmployee = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            var listEmployeeId = employeeService.getIdsByListVisa(listEmployee);

            if(project.getGroupId() == 0){
                var newGroupLeaderId = (Long) listEmployeeId.get(0);
                var group = new Group(0,newGroupLeaderId,1);
                var newGroupId = groupService.createNewGroup(group);
                project.setGroupId(newGroupId);
            }
            long newProjectId = projectService.createNewProject(project);
            projectEmployeeService.saveAllEmployeeToNewProject(newProjectId, listEmployeeId);

            var response = new Response();
            response.setStatusCode("201");
            response.setStatusMsg("Project saved successfully");
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } catch (JsonProcessingException | ProjectNumberAlreadyExistsException e) {
            var response = new Response();
            response.setStatusCode("400");
            response.setStatusMsg("Project saved failed");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
    }

    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<Response> saveProjectChange(@RequestBody String jsonObject){
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), Project.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);

            projectService.updateProjectWithListEmployeeVisa(project, listEmployeeVisa);

            var response = new Response();
            response.setStatusCode("201");
            response.setStatusMsg("Project saved successfully");
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } catch (JsonProcessingException e) {
            var response = new Response();
            response.setStatusCode("400");
            response.setStatusMsg("Project saved failed");
            System.out.println(response);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
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

    @GetMapping(value = "/search")
    public List<Project> searchProjectCriteriaSpecified(@RequestParam(value = "proCriteria") String proName, @RequestParam(value = "proStatus") String proStatus){
        return projectService.getProjectByCriteria(proName, proStatus);
    }
}
