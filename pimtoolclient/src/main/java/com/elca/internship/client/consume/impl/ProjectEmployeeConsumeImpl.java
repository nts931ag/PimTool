package com.elca.internship.client.consume.impl;

import com.elca.internship.client.api.ProjectEmployeeRestClient;
import com.elca.internship.client.consume.ProjectEmployeeConsume;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectEmployeeConsumeImpl implements ProjectEmployeeConsume {
    private final ProjectEmployeeRestClient projectEmployeeRestClient;
    @Override
    public ObservableList<String> retrieveAllEmployeeVisaByProjectId(Long projectId) {
        return FXCollections.observableArrayList(projectEmployeeRestClient.getAllEmployeeVisaByProjectId(projectId));
    }
}
