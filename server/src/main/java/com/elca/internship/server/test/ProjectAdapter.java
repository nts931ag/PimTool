package com.elca.internship.server.test;

import com.elca.internship.server.models.Response;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistedException;
import com.elca.internship.server.services.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectAdapter {
    private final ProjectService projectService;
    private final ObjectMapper objectMapper;

    public void createNewProject(String jsonObject){
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), Project.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            projectService.createNewProjectWithEmployeeVisasTest(project, listEmployeeVisa);
        } catch (JsonProcessingException jpe) {
            System.out.println(jpe.getMessage());
        }
    }

    public void updateProject(String jsonObject) {
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), Project.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            projectService.updateProjectWithEmployeeVisasTest(project, listEmployeeVisa);
        } catch (JsonProcessingException jpe) {
            System.out.println(jpe.getMessage());
        }
    }
}
