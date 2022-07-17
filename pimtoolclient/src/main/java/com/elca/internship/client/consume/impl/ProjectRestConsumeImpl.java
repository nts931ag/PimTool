package com.elca.internship.client.consume.impl;

import com.elca.internship.client.api.ProjectRestClient;
import com.elca.internship.client.consume.ProjectRestConsume;
import com.elca.internship.client.models.entity.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@RequiredArgsConstructor
public class ProjectRestConsumeImpl implements ProjectRestConsume {

    private final ProjectRestClient projectRestClient;

    @Override
    public List<Project> retrieveAllProjects() {
        return projectRestClient.getAllProjects();
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
}
