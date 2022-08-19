package com.elca.internship.client.adapter.impl;

import com.elca.internship.client.adapter.GroupAdapter;
import com.elca.internship.client.api.GroupRest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupAdapterImpl implements GroupAdapter {
    private final GroupRest groupRest;
    @Override
    public ObservableList<String> retrieveObsListIdsGroup() {
//        var listGroup = new ArrayList<Integer>();
//        groupRest.getAllIdGroup().subscribe(listGroup::add);
        var listGroup = groupRest.getAllIdGroup().collectList().block();
        var listIdGroup = listGroup.stream().map(Object::toString).collect(Collectors.toList());
        return FXCollections.observableArrayList(listIdGroup);
    }
}
