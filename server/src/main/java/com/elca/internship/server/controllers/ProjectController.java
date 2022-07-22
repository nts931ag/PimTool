package com.elca.internship.server.controllers;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.EmployeeNotExistedException;
import com.elca.internship.server.models.exceptions.GroupNotExistedException;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistedException;
import com.elca.internship.server.services.EmployeeService;
import com.elca.internship.server.services.GroupService;
import com.elca.internship.server.services.ProjectEmployeeService;
import com.elca.internship.server.services.ProjectService;
import com.elca.internship.server.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Response createNewProject(@RequestBody String jsonObject){
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), Project.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);

            projectService.createNewProjectWithEmployeeVisas(project, listEmployeeVisa);

            return new Response(false, "Create new project successfully!");

        } catch (JsonProcessingException | ProjectNumberAlreadyExistedException | EmployeeNotExistedException | GroupNotExistedException e) {
            return new Response(true, e.getMessage());
        }
    }

    @PutMapping(value = "/update", consumes = "application/json")
    public Response saveProjectChange(@RequestBody String jsonObject){
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), Project.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);

            projectService.updateProjectWithListEmployeeVisa(project, listEmployeeVisa);

            return new Response(false, "Save project successfully!");
        } catch (JsonProcessingException | GroupNotExistedException | EmployeeNotExistedException e) {
            return new Response(true, e.getMessage());
        }
    }


    @DeleteMapping(value = "/{id}")
    public Response deleteProject(@PathVariable(value = "id") Long projectId){
        try{
            var project = projectService.getProjectById(projectId);
            projectEmployeeService.removeProjectEmployeeByProjectId(projectId);
            projectService.deleteProject(project);
            return new Response(false, "delete project successfully!");

        }catch (EmptyResultDataAccessException e){
            return new Response(true, "Delete project failed!");
        }
    }

    @DeleteMapping(value = "/delete")
    public Response deleteProjects(@RequestParam(value = "Ids") List<Long> Ids){
        projectService.deleteProjectsByIds(Ids);
        return new Response(false, "Delete project successfully");
    }

    @GetMapping(value = "/search")
    public List<Project> searchProjectCriteriaSpecified(@RequestParam(value = "proCriteria") String proName, @RequestParam(value = "proStatus") String proStatus){
        return projectService.getProjectByCriteria(proName, proStatus);
    }

    @GetMapping(value = "/{proNum}")
    public Long getProjectNumber(@PathVariable(value = "proNum") Long proNum){
        try{
            return projectService.findProjectNumber(proNum);
        }catch (EmptyResultDataAccessException e){
            return 0L;
        }
    }
}
