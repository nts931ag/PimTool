package com.elca.internship.client.consume.impl;

import com.elca.internship.client.api.old.GroupRestClient;
import com.elca.internship.client.consume.GroupRestConsume;
import com.elca.internship.client.models.entity.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupRestConsumeImpl implements GroupRestConsume {
    private final GroupRestClient groupRestClient;
    @Override
    public ObservableList<String> retrieveObsListAllGroupIds() {
        return FXCollections.observableArrayList(groupRestClient.getAllProjects().stream().map(Group::getId).map(Object::toString).toList());
    }
}
