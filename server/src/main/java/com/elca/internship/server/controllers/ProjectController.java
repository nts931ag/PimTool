package com.elca.internship.server.controllers;

import com.elca.internship.server.mappers.PageProjectMapperCustom;
import com.elca.internship.server.mappers.ProjectMapperCustom;
import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.record.ProjectFormRecord;
import com.elca.internship.server.models.record.ProjectPageRecord;
import com.elca.internship.server.models.record.ProjectRecord;
import com.elca.internship.server.models.record.SearchProjectFormRecord;
import com.elca.internship.server.services.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectMapperCustom projectMapperCustom;
    private final PageProjectMapperCustom pageProjectMapperCustom;

    @GetMapping
    public List<ProjectRecord> getAllProjectExisted(){
        return projectMapperCustom.listEntityToListRecord(projectService.getAllProject());
    }

    @GetMapping("/search")
    public ProjectPageRecord searchProjectByCriteriaAndStatusSpecified(SearchProjectFormRecord searchForm){

        Pageable page = PageRequest.of(searchForm.offset(), searchForm.limit(), Sort.by("projectNumber").ascending());
        Status status = null;
        var statusList = Arrays.stream(Status.values()).collect(Collectors.toList())
                .stream().map(Object::toString).collect(Collectors.toList());

        if(statusList.contains(searchForm.proStatus())){
            status = Status.valueOf(searchForm.proStatus());
        }

        var pageProjectDto = projectService.getAllProjectByCriteriaAndStatusWithPagination(searchForm.proCriteria(), status, page);
        return pageProjectMapperCustom.projectPageToProjectPageRecord(pageProjectDto);
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
    public ProjectRecord getProjectByProjectNumber(@PathVariable("projectNumber") Integer projectNumber){
        var project = projectService.getProjectByProjectNumber(projectNumber);
        return projectMapperCustom.entityToRecord(project);
    }

    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<String> createNewProjectTest(@RequestBody ProjectFormRecord projectFormRecord){
        projectService.createNewProject(projectFormRecord.project(), projectFormRecord.listMember());

        return ResponseEntity.status(HttpStatus.OK).body("Create project Success");
    }

    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<String> updateProject(@RequestBody ProjectFormRecord projectFormRecord) {
        projectService.updateProject(projectFormRecord.project(), projectFormRecord.listMember());
        return ResponseEntity.status(HttpStatus.OK).body("update project Success");
    }
}
