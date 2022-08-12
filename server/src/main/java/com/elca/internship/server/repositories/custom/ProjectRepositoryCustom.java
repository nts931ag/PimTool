package com.elca.internship.server.repositories.custom;

import com.elca.internship.server.models.entity.Project;

public interface ProjectRepositoryCustom {
    Project findProjectByProjectNumber(Integer projectNumber);
}
