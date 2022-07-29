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
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

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
    public Response removeProjectById(Long projectId) {
        return projectRestClient.deleteById(projectId).block();
    }

    @Override
    public List<Project> searchProjectByCriteriaSpecified(String tfSearchValue, String cbStatusValue) {
        return FXCollections.observableArrayList(
                projectRestClient.getAllProjectsByCriteriaSpecified(tfSearchValue, cbStatusValue)
        );
    }

    @Override
    public Response saveProjectChange(Project project, List<String> listMember) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        var map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("listMember", listMember);
        var jsonObject = objectMapper.writeValueAsString(map);
        return projectRestClient.updateProject(jsonObject).block();
    }

    @Override
    public Response createNewProject(Project project, List<String> listMember) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        var map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("listMember", listMember);
        var jsonObject = objectMapper.writeValueAsString(map);
        return projectRestClient.saveNewProject(jsonObject).block();
    }

    @Override
    public void removeProjectsByIds(List<Long> listIdDelete) {

        var response = projectRestClient.deleteByIds(listIdDelete).block();
    }

    @Override
    public Boolean CheckProjectNumberIsExisted(Long projectNumber) {
        var proNum = projectRestClient.getProjectNumber(projectNumber).block();
        if(proNum == 0){
            return false;
        }
        return true;
    }

    @Override
    public ObservableList<Project> retrieveProjectsWithPagination(String tfSearchValue, String cbStatusValue, int pageIndex, int itemPerPage) {
        return FXCollections.observableArrayList(projectRestClient.getProjectsByCriteriaSpecifiedWithPagination(tfSearchValue, cbStatusValue, pageIndex, itemPerPage));
    }

    @Override
    public void createNewProjectTest(Project project, List<String> listMember) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        var map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("listMember", listMember);
        var jsonObject = objectMapper.writeValueAsString(map);
        projectRestClient.createNewProjectTest(jsonObject);
    }

    @Override
    public void updateProjectTest(Project project, List<String> listMember) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        var map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("listMember", listMember);
        var jsonObject = objectMapper.writeValueAsString(map);
        projectRestClient.updateNewProjectTest(jsonObject);
    }
}
