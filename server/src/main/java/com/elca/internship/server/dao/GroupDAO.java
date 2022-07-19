package com.elca.internship.server.dao;

import com.elca.internship.server.models.entity.Group;

import java.util.List;

public interface GroupDAO {
    List<Group> findAll();

    Long insert(Group group);

    Group findById(long groupId);
}
