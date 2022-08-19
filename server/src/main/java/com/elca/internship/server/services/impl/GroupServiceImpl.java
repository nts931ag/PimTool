package com.elca.internship.server.services.impl;

import com.elca.internship.server.models.entity.BaseEntity;
import com.elca.internship.server.repositories.GroupRepository;
import com.elca.internship.server.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    @Override
    public List<Long> getAllIdsGroup() {
        var listGroup = groupRepository.findAll();
        return listGroup.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }
}
