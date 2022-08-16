package com.elca.internship.client.adapter.impl;

import com.elca.internship.client.adapter.ProjectAdapter;
import com.elca.internship.client.api.news.ProjectRest;
import com.elca.internship.client.controllers.DashboardController;
import com.elca.internship.client.controllers.ViewListProjectController;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.ProjectTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.javafx.IconNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectAdapterImpl implements ProjectAdapter {
    private final ProjectRest projectRest;
    private final ObjectMapper objectMapper;

    @Override
    public ObservableList<ProjectTable> getAndConfigAllProjectTableData(ViewListProjectController viewListProjectController) {
        ObservableList<ProjectTable> listProjectTable = FXCollections.observableArrayList();
        projectRest.getAllProject().subscribe(project -> {
            var projectTable = new ProjectTable(
                    new CheckBox(),
                    project.getId(),
                    project.getGroupId(),
                    project.getProjectNumber(),
                    project.getName(),
                    project.getCustomer(),
                    project.getStatus(),
                    project.getStartDate(),
                    project.getEndDate(),
                    project.getVersion(),
                    new IconNode(GoogleMaterialDesignIcons.DELETE)
            );

            if(projectTable.getStatus().toString().equalsIgnoreCase("new")){
                projectTable.getIcDelete().getStyleClass().add("icon-node");
                projectTable.getIcDelete().setOnMouseClicked(viewListProjectController.deleteItemHandler());
                viewListProjectController.configureCheckBox(projectTable.getCheckBox());
            }else{
                projectTable.setIcDelete(null);
                projectTable.getCheckBox().setDisable(true);
            }
            projectTable.getLbProNumLink().getStyleClass().add("lb-super-link");
            projectTable.getLbProNumLink().setOnMouseClicked(event -> {
                DashboardController.navigationHandler.handleNavigateToEditProject(projectTable);
            });
            listProjectTable.add(projectTable);
        });


        return listProjectTable;
    }

    @Override
    public void deleteProjectById(Long id) {
        projectRest.deleteProjectById(id);
    }

    @Override
    public void deleteProjectByIds(List<Long> listIdDelete) {
        projectRest.deleteProjectByIds(listIdDelete);
    }

    @Override
    public Boolean checkProjectNumberIsExisted(Integer projectNumber) {
        var project = projectRest.getProjectByProjectNumber(projectNumber).block();
        if(project == null ){
            return false;
        }
        return true;
    }

    private String convertToJsonString(Project project, List<String> listMember) throws JsonProcessingException {
        var map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("listMember", listMember);
        return objectMapper.writeValueAsString(map);
    }

    @Override
    public void createNewProject(Project project, List<String> listMember) throws JsonProcessingException {
        var jsonObject = convertToJsonString(project, listMember);
        projectRest.createNewProject(jsonObject);
    }

    @Override
    public void updateProject(Project project, List<String> listMember) throws JsonProcessingException {
        var jsonObject = convertToJsonString(project, listMember);
        projectRest.updateProject(jsonObject);
    }

}
