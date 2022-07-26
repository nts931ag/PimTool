package com.elca.internship.server.controllers;

import com.elca.internship.server.models.Response;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.EmployeeNotExistedException;
import com.elca.internship.server.models.exceptions.GroupNotExistedException;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistedException;
import com.elca.internship.server.services.EmployeeService;
import com.elca.internship.server.services.GroupService;
import com.elca.internship.server.services.ProjectEmployeeService;
import com.elca.internship.server.services.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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

            return new Response(0, "Create new project successfully!");

        } catch (JsonProcessingException | ProjectNumberAlreadyExistedException  e) {
            return new Response(1, e.getMessage());
        } catch ( EmployeeNotExistedException enee){
            return new Response(2, enee.getMessage());
        } catch ( GroupNotExistedException gnee){
            return new Response(3, gnee.getMessage());
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

            return new Response(0, "Save project successfully!");
        } catch (JsonProcessingException | EmployeeNotExistedException enee) {
            return new Response(2, enee.getMessage());
        }catch ( GroupNotExistedException gnee){
            return new Response(3, gnee.getMessage());
        }
    }


    @DeleteMapping(value = "/{id}")
    public Response deleteProject(@PathVariable(value = "id") Long projectId){
        try{
            var project = projectService.getProjectById(projectId);
            projectEmployeeService.removeProjectEmployeeByProjectId(projectId);
            projectService.deleteProject(project);
            return new Response(0, "delete project successfully!");

        }catch (EmptyResultDataAccessException e){
            return new Response(1, "Delete project failed!");
        }
    }

    @DeleteMapping(value = "/delete")
    public Response deleteProjects(@RequestParam(value = "Ids") List<Long> Ids){
        projectService.deleteProjectsByIds(Ids);
        return new Response(0, "Delete project successfully");
    }

    @GetMapping(value = "/search")
    public List<Project> searchProjectCriteriaSpecified(@RequestParam(value = "proCriteria") String proCriteria, @RequestParam(value = "proStatus") String proStatus){
        return projectService.getProjectByCriteria(proCriteria, proStatus);
    }

    @GetMapping(value = "/{proNum}")
    public Long getProjectNumber(@PathVariable(value = "proNum") Long proNum){
        try{
            return projectService.findProjectNumber(proNum);
        }catch (EmptyResultDataAccessException e){
            return 0L;
        }
    }

    @GetMapping(value = "/test/search")
    public List<Project> searchProjectCriteriaSpecifiedWithPagination(
            @RequestParam(value = "proCriteria") String proCriteria,
            @RequestParam(value = "proStatus") String proStatus,
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestParam(value = "pageSize") Integer pageSize
    ){
//        log.info("{} {} {} {}", proCriteria, proStatus, pageNumber, pageSize);
        return projectService.getProjectByCriteriaWithPagination(proCriteria, proStatus, pageNumber, pageSize);
    }
}
