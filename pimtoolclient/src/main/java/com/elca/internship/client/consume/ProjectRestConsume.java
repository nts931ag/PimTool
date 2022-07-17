package com.elca.internship.client.consume;

import com.elca.internship.client.models.entity.Project;
import javafx.collections.ObservableList;

import java.util.List;

public interface ProjectRestConsume {
    List<Project> retrieveAllProjects();
    ObservableList<Integer> retrieveAllProjectNumbers();
    String removeProjectById(Long projectId);
}
