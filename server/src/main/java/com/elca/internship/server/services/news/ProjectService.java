package com.elca.internship.server.services.news;

import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.Project;

import java.util.List;

public interface ProjectService {

    Project createNewProject(ProjectDto projectDto, List<String> listVisaEmployee);
    Project updateProject(ProjectDto projectDto, List<String> listVisaEmployee);

    void deleteProject(Long id);

}
