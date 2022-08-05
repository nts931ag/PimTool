package com.elca.internship.server.services.impl;

import com.elca.internship.server.dao.GroupDAO;
import com.elca.internship.server.models.entity.Group;
import com.elca.internship.server.repositories.GroupRepository;
import com.elca.internship.server.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupDAO groupDAO;
    private final GroupRepository groupRepository;

    @Override
    public List<Group> getAll() {
        return groupRepository.findAll();
//        return groupDAO.findAll();
    }

    @Override
    public Long createNewGroup(Group group) {
        return groupRepository.save(group).getId();
//        return groupDAO.insert(group);
    }
}
