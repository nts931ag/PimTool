package com.elca.internship.client.consume.impl;

import com.elca.internship.client.api.GroupRestClient;
import com.elca.internship.client.consume.GroupRestConsume;
import com.elca.internship.client.models.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupRestConsumeImpl implements GroupRestConsume {
    private final GroupRestClient groupRestClient;
    @Override
    public List<Group> retrieveAllGroups() {
        return groupRestClient.getAllProjects();
    }
}
