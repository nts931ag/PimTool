package com.elca.internship.client.adapter;

import com.elca.internship.client.controllers.ViewListProjectController;
import com.elca.internship.client.models.entity.ProjectTable;
import javafx.collections.ObservableList;

import java.util.List;

public interface ProjectAdapter {
    ObservableList<ProjectTable> getAndConfigAllProjectTableData(ViewListProjectController viewListProjectController);
    void deleteProjectById(Long id);

    void deleteProjectByIds(List<Long> listIdDelete);
}
