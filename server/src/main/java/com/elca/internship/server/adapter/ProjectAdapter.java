package com.elca.internship.server.adapter;

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

    public void updateProject(String jsonObject) {
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), Project.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            log.info("Info new project: " + project);
            log.info("Info list member: " + listEmployeeVisa);
//            projectService.updateProjectWithEmployeeVisasTest(project, listEmployeeVisa);
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
//            projectService.createNewProjectWithEmployeeVisasTest(project, listEmployeeVisa);



        } catch (JsonProcessingException jpe) {
            System.out.println(jpe.getMessage());
        }


    }

    public List<ProjectDto> getProjectByCriteriaAndStatusSpecified(String proCriteria, String proStatus) {
        if(!proCriteria.isBlank() && !proStatus.isBlank()){
            return projectService.getAllProjectsByCriteriaAndStatus(proCriteria, Status.valueOf(proStatus));
        }else if(proCriteria.isBlank() && proStatus.isBlank()){
            return projectService.getAllProject();
        }else if(proCriteria.isBlank()){
            return projectService.getAllProjectByStatus(Status.valueOf(proStatus));
        }else {
            return projectService.getAllProjectByCriteria(proCriteria);
        }
    }
}
