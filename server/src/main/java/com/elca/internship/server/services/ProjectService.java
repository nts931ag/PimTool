package com.elca.internship.server.services;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.EmployeeNotExistedException;
import com.elca.internship.server.models.exceptions.GroupNotExistedException;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistedException;
import org.springframework.data.relational.core.sql.In;

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

    Integer findProjectNumber(Integer proNum);

    List<Project> getProjectByCriteriaWithPagination(String proName, String proStatus, Integer pageNumber, Integer pageSize);

    void createNewProjectWithEmployeeVisasTest(Project project, List<String> listEmployeeVisa);

    boolean checkProjectNumberExisted(Integer projectNumber);

    void updateProjectWithEmployeeVisasTest(Project project, List<String> listEmployeeVisa);
}
