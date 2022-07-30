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
import com.elca.internship.server.test.ErrorResponse;
import com.elca.internship.server.test.ProjectAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping(value = "/update", consumes = "application/json")
    public Response saveProjectChange(@RequestBody String jsonObject){
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), Project.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            log.info("Project info: {}, list employee visa: {}", project, listEmployeeVisa);
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
            log.info("Project will be deleted: {}", project);
            projectEmployeeService.removeProjectEmployeeByProjectId(projectId);
            projectService.deleteProject(project);
            return new Response(0, "delete project successfully!");
        }catch (EmptyResultDataAccessException e){
            return new Response(1, "Delete project failed!");
        }
    }

    @DeleteMapping(value = "/delete")
    public Response deleteProjects(@RequestParam(value = "Ids") List<Long> Ids){
        log.info("List id project: ", Ids);
        projectService.deleteProjectsByIds(Ids);
        return new Response(0, "Delete project successfully");
    }

    @GetMapping(value = "/search")
    public List<Project> searchProjectCriteriaSpecified(@RequestParam(value = "proCriteria") String proCriteria, @RequestParam(value = "proStatus") String proStatus){
        return projectService.getProjectByCriteria(proCriteria, proStatus);
    }

    @GetMapping(value = "/{proNum}")
    public Integer getProjectNumber(@PathVariable(value = "proNum") Integer proNum){
        log.info("Project number: {}", proNum);
        try{
            return projectService.findProjectNumber(proNum);
        }catch (EmptyResultDataAccessException e){
            return 0;
        }
    }

    @GetMapping(value = "/test/search")
    public List<Project> searchProjectCriteriaSpecifiedWithPagination(
            @RequestParam(value = "proCriteria") String proCriteria,
            @RequestParam(value = "proStatus") String proStatus,
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestParam(value = "pageSize") Integer pageSize
    ){
        log.info("Criteria value: {}, status value: {}, page number: {}, page size: {}", proCriteria, proStatus, pageNumber, pageSize);
        return projectService.getProjectByCriteriaWithPagination(proCriteria, proStatus, pageNumber, pageSize);
    }
    private final ProjectAdapter projectAdapter;
    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<String> createNewProjectTest(@RequestBody String jsonObject){
        log.info("Json object from client: " + jsonObject);
        projectAdapter.createNewProject(jsonObject);
        return ResponseEntity.status(HttpStatus.OK).body("Create project Success");
    }


    @PutMapping(value = "/test/update", consumes = "application/json")
    public ResponseEntity<String> saveProjectChangeTest(@RequestBody String jsonObject){
        log.info("Json object from client: " + jsonObject);
        projectAdapter.updateProject(jsonObject);
        return ResponseEntity.status(HttpStatus.OK).body("Create project Success");
    }
}
