package com.elca.internship.server.repositories.custom;

import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectRepositoryCustom {
    Project findProjectByProjectNumberCustom(Integer projectNumber);

    Project findProjectByIdCustom(Long projectId);

    List<Project> findAllCustom();

    Page<Project> findAllProjectByCriteriaAndStatusWithPaginationCustom(String criteria, Status status, Pageable page);
}
