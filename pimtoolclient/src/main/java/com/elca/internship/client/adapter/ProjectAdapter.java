package com.elca.internship.client.adapter;

import com.elca.internship.client.controllers.ViewListProjectController;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.ProjectTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.util.List;

public interface ProjectAdapter {
    ObservableList<ProjectTable> getAndConfigAllProjectTableData(ViewListProjectController viewListProjectController);
    void deleteProjectById(Long id);

    void deleteProjectByIds(List<Long> listIdDelete);

    Boolean checkProjectNumberIsExisted(Integer parseLong);

    void createNewProject(Project project, List<String> listMember) throws JsonProcessingException;

    void updateProject(Project project, List<String> listMember) throws JsonProcessingException;

    ObservableSet retrieveProjectWithCriteriaAndStatusSpecified(String tfSearchValue, String cbStatusValue);
}
