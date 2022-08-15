package com.elca.internship.server.repositories.custom;

import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.entity.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    Project findProjectByProjectNumberCustom(Integer projectNumber);

    Project findProjectByIdCustom(Long projectId);

    List<Project> findAllProjectByCriteriaAndStatusCustom(String criteria, Status status);


    List<Project> findAllProjectByStatusCustom(Status status);

    List<Project> findAllProjectByCriteriaCustom(String criteria);
}
