package com.elca.internship.server.services.news;

import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.Project;

import java.util.List;

public interface ProjectService {

    Project createNewProject(ProjectDto projectDto, List<String> listVisaEmployee);
    Project updateProject(ProjectDto projectDto, List<String> listVisaEmployee);
    void deleteProjects(List<Long> ids);
    void deleteProject(Long id);
    List<Project> getAllProjectsByCriteriaAndStatus(String criteria, Status status);
    List<Project> getAllProjectByCriteria(String criteria);
    List<Project> getAllProjectByStatus(Status status);

    List<Project> getAllProject();

    Project getProjectByProjectNumber(Integer projectNumber);
}
