package com.elca.internship.server.adapter;

import com.elca.internship.server.mappers.ProjectMapperCustom;
import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.services.news.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectAdapter {
    private final ProjectService projectService;
    private final ObjectMapper objectMapper;
    private final ProjectMapperCustom projectMapperCustom;
    private final EmployeeAdapter employeeAdapter;

    public void createNewProject(String jsonObject){
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), Project.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            log.info("Info new project: " + project);
            log.info("Info list member: " + listEmployeeVisa);
//            projectService.createNewProjectWithEmployeeVisasTest(project, listEmployeeVisa);
        } catch (JsonProcessingException jpe) {
            System.out.println(jpe.getMessage());
        }
    }

    public void updateAndModifyMemberOfProjectSpecified(String jsonObject) {
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), ProjectDto.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            log.info("Info new project: " + project);
            log.info("Info list member: " + listEmployeeVisa);
//            projectService.updateProjectWithEmployeeVisasTest(project, listEmployeeVisa);
            projectService.updateProject(project, listEmployeeVisa);
        } catch (JsonProcessingException jpe) {
            System.out.println(jpe.getMessage());
        }
    }

    public void createAndAddMembersIntoNewProject(String jsonObject){
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), ProjectDto.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            log.info("Info new project: " + project);
            log.info("Info list member: " + listEmployeeVisa);
            projectService.createNewProject(project, listEmployeeVisa);
        } catch (JsonProcessingException jpe) {
            System.out.println(jpe.getMessage());
        }


    }

    public List<ProjectDto> getProjectByCriteriaAndStatusSpecified(String proCriteria, String proStatus,Integer limit, Integer offset) {
        Pageable page = PageRequest.of(offset, limit, Sort.by("projectNumber").ascending());
        var criteriaExp = "%" + proCriteria.trim() + "%";
        Status status = null;
        if(!proStatus.isBlank()){
            status = Status.valueOf(proStatus);
        }
        var listProjectEntity = projectService.getAllProjectByCriteriaAndStatusWithPagination(criteriaExp, status, page);
        return projectMapperCustom.listEntityToListDto(listProjectEntity);
    }

    public ProjectDto getProjectByProjectNumber(Integer projectNumber) {
        var project = projectService.getProjectByProjectNumber(projectNumber);
        return projectMapperCustom.entityToDto(project);
    }
}
