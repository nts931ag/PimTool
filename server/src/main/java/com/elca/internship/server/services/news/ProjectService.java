package com.elca.internship.server.services.news;

import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.Project;

public interface ProjectService {

    Project createNewProject(ProjectDto projectDto);

}
