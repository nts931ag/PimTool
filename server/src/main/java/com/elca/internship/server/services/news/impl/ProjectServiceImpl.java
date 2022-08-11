package com.elca.internship.server.services.news.impl;

import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.repositories.EmployeeRepository;
import com.elca.internship.server.repositories.ProjectRepository;
import com.elca.internship.server.services.news.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;


    @Override
    public Project createNewProject(ProjectDto projectDto) {

        /*var listEmployeeExisted =

        var project = new Project(
                null,
                null,
                null,
                projectDto.getProjectNumber(),
                projectDto.getName(),
                projectDto.getCustomer(),
                projectDto.getStatus(),
                projectDto.getStartDate(),
                projectDto.getEndDate(),
                null
        );

        return projectRepository.save(project);*/
        return null;
    }
}
