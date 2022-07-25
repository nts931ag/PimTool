package com.elca.internship.server.services;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.EmployeeNotExistedException;
import com.elca.internship.server.models.exceptions.GroupNotExistedException;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistedException;

import java.util.List;

public interface ProjectService {
    Long createNewProject(Project project) throws ProjectNumberAlreadyExistedException;

    Project getProjectById(Long id);

    int updateProject(Long id, Project project);

    List<Project> getAllProject();

    void deleteProject(Project project);

    List<Project> getProjectByCriteria(String proCriteria, String proStatus);

    void updateProjectWithListEmployeeVisa(Project project, List<String> listEmployeeVisa) throws EmployeeNotExistedException, GroupNotExistedException;

    void createNewProjectWithEmployeeVisas(Project project, List<String> listEmployeeVisa) throws ProjectNumberAlreadyExistedException, EmployeeNotExistedException, GroupNotExistedException;

    void deleteProjectsByIds(List<Long> Ids);

    Long findProjectNumber(Long proNum);
}
