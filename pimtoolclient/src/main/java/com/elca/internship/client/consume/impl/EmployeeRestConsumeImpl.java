package com.elca.internship.client.consume.impl;

import com.elca.internship.client.api.EmployeeRestClient;
import com.elca.internship.client.consume.EmployeeRestConsume;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeRestConsumeImpl implements EmployeeRestConsume {
    private final EmployeeRestClient employeeRestClient;
    @Override
    public ObservableList<String> retrieveAllEmployees() {
        return null;
    }

    @Override
    public ObservableList<String> retrieveVisaAndNameOfAllEmployees() {
        return FXCollections.observableArrayList(employeeRestClient.getVisaAndNameOfAllEmployees());
    }
}
