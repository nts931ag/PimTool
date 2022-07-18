package com.elca.internship.server.services;

import com.elca.internship.server.models.entity.Group;

import java.util.List;

public interface GroupService {
    List<Group> getAll();

    Long createNewGroup(Group group);
}
