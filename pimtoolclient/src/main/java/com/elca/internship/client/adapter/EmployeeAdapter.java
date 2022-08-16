package com.elca.internship.client.adapter;

import javafx.collections.ObservableList;

import java.util.List;

public interface EmployeeAdapter {

    ObservableList<String> retrieveVisaAndNameOfAllEmployee();

    ObservableList<String> retrieveVisaAndNameOfMemberInCurrentProject(Long currentIdEdit);
}
