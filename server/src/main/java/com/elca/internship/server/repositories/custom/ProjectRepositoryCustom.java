package com.elca.internship.server.repositories.custom;

import com.elca.internship.server.models.entity.Project;

public interface ProjectRepositoryCustom {
    Project findProjectByProjectNumberCustom(Integer projectNumber);

    Project findProjectByIdCustom(Long projectId);
}
