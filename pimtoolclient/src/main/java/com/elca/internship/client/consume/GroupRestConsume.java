package com.elca.internship.client.consume;

import com.elca.internship.client.models.entity.Group;
import com.elca.internship.client.models.entity.Project;
import javafx.collections.ObservableList;

import java.util.List;

public interface GroupRestConsume {
    ObservableList<String> retrieveObsListAllGroupIds();

}
