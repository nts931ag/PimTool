package com.elca.internship.client.consume;

import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.ObservableList;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface ProjectRestConsume {
    ObservableList<Project> retrieveAllProjects();
    ObservableList<Integer> retrieveAllProjectNumbers();
    Response removeProjectById(Long projectId);

    Response saveProjectChange(Project project, List<String> listMember) throws JsonProcessingException;

    List<Project> searchProjectByCriteriaSpecified(String tfSearchValue, String cbStatusValue);

    Response createNewProject(Project project, List<String> listMember) throws JsonProcessingException;

    void removeProjectsByIds(List<Long> listIdDelete);

    Boolean CheckProjectNumberIsExisted(Long projectNumber);
}
