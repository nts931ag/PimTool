package com.elca.internship.server.services;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistsException;

import java.util.List;

public interface ProjectService {
    Long createNewProject(Project project) throws ProjectNumberAlreadyExistsException;

    Project getProject(Long id);

    Project updateProject(Long id, Project project);

    List<Project> getAllProject();

    void deleteProject(Project project);

    List<Project> getProjectByCriteria(String proCriteria, String proStatus);
}
