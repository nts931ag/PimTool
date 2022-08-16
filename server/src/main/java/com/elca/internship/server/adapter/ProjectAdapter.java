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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectAdapter {
    private final ProjectService projectService;
    private final ObjectMapper objectMapper;
    private final ProjectMapperCustom projectMapperCustom;

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

    public List<ProjectDto> getProjectByCriteriaAndStatusSpecified(String proCriteria, String proStatus) {
        List<Project> listProjectEntity;
        if(!proCriteria.isBlank() && !proStatus.isBlank()){
            listProjectEntity = projectService.getAllProjectsByCriteriaAndStatus(proCriteria, Status.valueOf(proStatus));
        }else if(proCriteria.isBlank() && proStatus.isBlank()){
            listProjectEntity = projectService.getAllProject();
        }else if(proCriteria.isBlank()){
            listProjectEntity = projectService.getAllProjectByStatus(Status.valueOf(proStatus));
        }else {
            listProjectEntity = projectService.getAllProjectByCriteria(proCriteria);
        }

        return projectMapperCustom.listEntityToListDto(listProjectEntity);
    }

    public ProjectDto getProjectByProjectNumber(Integer projectNumber) {
        var project = projectService.getProjectByProjectNumber(projectNumber);
        return projectMapperCustom.entityToDto(project);
    }
}
