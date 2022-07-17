package com.elca.internship.client.consume;

import com.elca.internship.client.models.entity.Group;
import com.elca.internship.client.models.entity.Project;

import java.util.List;

public interface GroupRestConsume {
    List<Group> retrieveAllGroups();

}
