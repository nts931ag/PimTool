package com.elca.internship.server.services;

import com.elca.internship.server.models.entity.Project;

import java.util.List;

public interface ProjectService {
    Long createNewProject(Project project);

    Project getProject(Long id);

    Project updateProject(Long id, Project project);

    List<Project> getAllProject();
}
