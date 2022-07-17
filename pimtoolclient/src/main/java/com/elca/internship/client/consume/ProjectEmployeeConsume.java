package com.elca.internship.client.consume;

import javafx.collections.ObservableList;

public interface ProjectEmployeeConsume {

    ObservableList<String> retrieveAllEmployeeVisasByProjectId(Long id);
}
