package com.elca.internship.client.adapter.impl;

import com.elca.internship.client.adapter.EmployeeAdapter;
import com.elca.internship.client.api.EmployeeRest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeAdapterImpl implements EmployeeAdapter {
    private final EmployeeRest employeeRest;
    @Override
    public ObservableList<String> retrieveVisaAndNameOfAllEmployee() {
        var listEmployee = employeeRest.getAllEmployee().collectList().block();
        return FXCollections.observableArrayList(
                listEmployee.stream()
                        .map(employee -> employee.getVisa() + ": " + employee.getFirstName() + " " + employee.getLastName())
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ObservableList<String> retrieveVisaAndNameOfMemberInCurrentProject(Long currentProjectId) {
        var listEmployee = employeeRest.getAllEmployeeOfCurrentProject(currentProjectId).collectList().block();
        return FXCollections.observableArrayList(
                listEmployee.stream()
                        .map(employee -> employee.getVisa() + ": " + employee.getFirstName() + " " + employee.getLastName())
                        .collect(Collectors.toList())
        );
    }
}
