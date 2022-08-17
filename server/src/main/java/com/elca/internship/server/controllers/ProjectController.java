package com.elca.internship.server.controllers;

import com.elca.internship.server.mappers.ProjectMapperCustom;
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

    private final ProjectAdapter projectAdapter;
    private final ProjectService projectService;

    private final ProjectMapperCustom projectMapperCustom;

    @GetMapping
    public List<ProjectDto> getAllProjectExisted(){
        return projectMapperCustom.listEntityToListDto(projectService.getAllProject());
    }

    @GetMapping("/search")
    public List<ProjectDto> searchProjectByCriteriaAndStatusSpecified(
            @RequestParam(value = "proCriteria") String proCriteria, @RequestParam(value = "proStatus") String proStatus,
            @RequestParam(value = "limit") Integer limit, @RequestParam(value = "offset") Integer offset){
        return projectAdapter.getProjectByCriteriaAndStatusSpecified(proCriteria, proStatus, limit, offset);
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

    @GetMapping(value = "/{projectNumber}")
    public ProjectDto getProjectByProjectNumber(@PathVariable("projectNumber") Integer projectNumber){
        return projectAdapter.getProjectByProjectNumber(projectNumber);
    }
}
