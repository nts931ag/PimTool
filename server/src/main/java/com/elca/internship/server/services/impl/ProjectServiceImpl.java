package com.elca.internship.server.services.impl;

import com.elca.internship.server.dao.ProjectDAO;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectServiceImpl implements ProjectService {
    ProjectDAO projectDAO;

    @Autowired
    public ProjectServiceImpl(ProjectDAO projectDAO){
        this.projectDAO = projectDAO;
    }

    @Override
    public Long createNewProject(Project project) {
        return projectDAO.insert(project);
    }

    @Override
    public Project getProject(Long id) {
        return projectDAO.findById(id);
    }

    @Override
    public Project updateProject(Long id, Project project) {
        return projectDAO.update(id, project);
    }

    @Override
    public List<Project> getAllProject() {
        return projectDAO.findAll();
    }
}
