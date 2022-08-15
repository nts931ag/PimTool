package com.elca.internship.server.validator;

import com.elca.internship.server.exceptions.GroupNotExistedException;
import com.elca.internship.server.models.entity.Group;
import com.elca.internship.server.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupValidator {
    private final GroupRepository groupRepository;
    public Group validateAndGetGroupIfExisted(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(
                () -> new GroupNotExistedException(groupId)
        );
    }
}
