package com.elca.internship.server.controllers;

import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.adapter.ProjectAdapter;
import com.elca.internship.server.services.news.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    /*private final ProjectService projectService;
    private final ProjectEmployeeService projectEmployeeService;
    private final ProjectAdapter projectAdapter;

    @GetMapping()
    public List<Project> getAllProject(){
        return projectService.getAllProject();
    }

    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<String> createNewProjectTest(@RequestBody String jsonObject){
        log.info("Json object from client: " + jsonObject);
        projectAdapter.createNewProject(jsonObject);
        return ResponseEntity.status(HttpStatus.OK).body("Create project Success");
    }


    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<String> saveProjectChangeTest(@RequestBody String jsonObject){
        log.info("Json object from client: " + jsonObject);
        projectAdapter.updateProject(jsonObject);
        return ResponseEntity.status(HttpStatus.OK).body("Create project Success");
    }

    // should convert to find size of CriteriaSpecified
    @GetMapping(value = "/search")
    public List<Project> searchProjectCriteriaSpecified(@RequestParam(value = "proCriteria") String proCriteria, @RequestParam(value = "proStatus") String proStatus){
        return projectService.getProjectByCriteria(proCriteria, proStatus);
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

    @GetMapping(value = "/{proNum}")
    public Integer getProjectNumber(@PathVariable(value = "proNum") Integer proNum){
        log.info("Project number: {}", proNum);
        try{
            return projectService.findProjectNumber(proNum);
        }catch (EmptyResultDataAccessException e){
            return 0;
        }
    }

    @GetMapping(value = "/search/size")
    public Integer getSizeOfResultSearch(
            @RequestParam(value = "proCriteria") String proCriteria,
            @RequestParam(value = "proStatus") String proStatus
    ){
        return projectService.getSizeOfResultSearch(proCriteria, proStatus);
    }*/

    private final ProjectAdapter projectAdapter;
    private final ProjectService projectService;

    @GetMapping("/search")
    public List<ProjectDto> searchProjectByCriteriaAndStatusSpecified(@RequestParam(value = "proCriteria") String proCriteria, @RequestParam(value = "proStatus") String proStatus){
        return projectAdapter.getProjectByCriteriaAndStatusSpecified(proCriteria, proStatus);
    }

    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<String> createNewProject(@RequestBody String jsonObject){
        log.info("Json object from client: " + jsonObject);
        projectAdapter.createAndAddMembersIntoNewProject(jsonObject);
        return ResponseEntity.status(HttpStatus.OK).body("Create project Success");
    }

    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<String> updateProject(@RequestBody String jsonObject) {
        log.info("Json object from client: " + jsonObject);
        projectAdapter.updateAndModifyMemberOfProjectSpecified(jsonObject);
        return ResponseEntity.status(HttpStatus.OK).body("update project Success");
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProject(@PathVariable(value = "id") Long projectId){
        log.info("Id project will be deleted: ", projectId);
        projectService.deleteProject(projectId);
    }

    @DeleteMapping(value = "/delete")
    public void deleteProjects(@RequestParam(value = "Ids") List<Long> Ids){
        log.info("List id project will be deleted: ", Ids);
        projectService.deleteProjects(Ids);
    }
}
