package com.elca.internship.client.consume.impl;

import com.elca.internship.client.api.ProjectRestClient;
import com.elca.internship.client.consume.ProjectRestConsume;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@RequiredArgsConstructor
public class ProjectRestConsumeImpl implements ProjectRestConsume {

    private final ProjectRestClient projectRestClient;

    @Override
    public ObservableList<Project> retrieveAllProjects() {
        return FXCollections.observableArrayList(projectRestClient.getAllProjects());
    }

    @Override
    public ObservableList<Integer> retrieveAllProjectNumbers() {
        var listProjects = projectRestClient.getAllProjectNumbers();
        return FXCollections.observableArrayList(listProjects);
    }

    @Override
    public String removeProjectById(Long projectId) {
        return projectRestClient.deleteById(projectId);
    }

    @Override
    public void saveProjectChange(Project project, List<String> listMember) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        var map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("listMember", listMember);
        var jsonObject = objectMapper.writeValueAsString(map);
        System.out.println(jsonObject);
        var response = projectRestClient.updateProject(jsonObject);
        System.out.println(response);
    }

    @Override
    public List<Project> searchProjectByCriteriaSpecified(String tfSearchValue, String cbStatusValue) {
        return FXCollections.observableArrayList(
                projectRestClient.getAllProjectsByCriteriaSpecified(tfSearchValue, cbStatusValue)
        );
    }

    @Override
    public ResponseEntity<Response> createNewProject(Project project, List<String> listMember) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        var map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("listMember", listMember);
        var jsonObject = objectMapper.writeValueAsString(map);
        return projectRestClient.saveNewProject(jsonObject).block();
    }


}
