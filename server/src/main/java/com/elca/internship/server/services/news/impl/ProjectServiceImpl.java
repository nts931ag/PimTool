package com.elca.internship.server.services.news.impl;

import com.elca.internship.server.exceptions.GroupLeaderNotExistedException;
import com.elca.internship.server.exceptions.ProjectNumberAlreadyExistedException;
import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.*;
import com.elca.internship.server.repositories.EmployeeRepository;
import com.elca.internship.server.repositories.GroupRepository;
import com.elca.internship.server.repositories.ProjectRepository;
import com.elca.internship.server.services.news.ProjectService;
import com.elca.internship.server.validator.EmployeeValidator;
import com.elca.internship.server.validator.ProjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final GroupRepository groupRepository;
    private final EmployeeValidator employeeValidator;
    private final ProjectValidator projectValidator;


    @Override
    @Transactional
    public Project createNewProject(ProjectDto projectDto, List<String> listVisaEmployee) {

        // validate project number existed

        var project = projectRepository.findByProjectNumber(projectDto.getProjectNumber());
        project.ifPresent(p -> {throw new ProjectNumberAlreadyExistedException(projectDto.getProjectNumber());});
        // validate project member
        var listEmployeeExisted = employeeRepository.findAllByVisaIn(listVisaEmployee);
        employeeValidator.validateEmployeesExisted(listEmployeeExisted, listVisaEmployee);
        // validate project group
        var group = groupRepository.findById(projectDto.getGroupId()).orElseGet(() -> {
            if(listEmployeeExisted.size() != 0){
                var newGroup = new Group(null, null,listEmployeeExisted.get(0), null);
                listEmployeeExisted.remove(0);
                return newGroup;
            }else{
                throw new GroupLeaderNotExistedException("Group Leader must not be empty");
            }
        });

        var newProject = new Project(
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

        newProject.setGroup(group);

        // create setProjectEmployee

        var setProjectEmployee = new HashSet<ProjectEmployee>();
        listEmployeeExisted.forEach(employee -> {
            var ProjectEmployeeKey = new ProjectEmployeeKey(projectDto.getId(), employee.getId());
            setProjectEmployee.add(
                    new ProjectEmployee(ProjectEmployeeKey,
                            newProject,
                            employee)
            );
        });
        newProject.setProjectEmployee(setProjectEmployee);

        // save new project
        return projectRepository.save(newProject);
    }

    @Override
    public Project updateProject(ProjectDto projectDto, List<String> listVisaEmployee) {
        // validate Project existed
        var projectUpdate = projectRepository.findProjectByProjectNumber(projectDto.getProjectNumber());
//        var projectUpdate = projectRepository.findByProjectNumber(projectDto.getProjectNumber()).get();
        // validate new project member
        var listEmployeeExisted = employeeRepository.findAllByVisaIn(listVisaEmployee);
        employeeValidator.validateEmployeesExisted(listEmployeeExisted, listVisaEmployee);
        // validate project group
        var group = groupRepository.findById(projectDto.getGroupId()).orElseGet(() -> {
            if(listEmployeeExisted.size() != 0){
                var newGroup = new Group(null, null,listEmployeeExisted.get(0), null);
                listEmployeeExisted.remove(0);
                return newGroup;
            }else{
                throw new GroupLeaderNotExistedException("Group Leader must not be empty");
            }
        });

        projectUpdate.setGroup(group);

        // create setProjectEmployee

        var setProjectEmployee = new HashSet<ProjectEmployee>();
        listEmployeeExisted.forEach(employee -> {
            var ProjectEmployeeKey = new ProjectEmployeeKey(projectDto.getId(), employee.getId());
            setProjectEmployee.add(
                    new ProjectEmployee(ProjectEmployeeKey,
                            projectUpdate,
                            employee)
            );
        });


        projectUpdate.setCustomer(projectDto.getCustomer());
        projectUpdate.setEndDate(projectDto.getEndDate());
        projectUpdate.setStartDate(projectDto.getStartDate());
        projectUpdate.setStatus(projectDto.getStatus());
        projectUpdate.setName(projectDto.getName());
        projectUpdate.setProjectEmployee(setProjectEmployee);
        // save new project
        return projectRepository.save(projectUpdate);
//        return null;
    }
}
