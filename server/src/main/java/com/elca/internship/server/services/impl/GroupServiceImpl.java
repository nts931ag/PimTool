package com.elca.internship.server.services.impl;

import com.elca.internship.server.dao.GroupDAO;
import com.elca.internship.server.models.entity.Group;
import com.elca.internship.server.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupDAO groupDAO;

    @Override
    public List<Group> getAll() {
        return groupDAO.findAll();
    }

    @Override
    public Long createNewGroup(Group group) {
        return groupDAO.insert(group);
    }
}
