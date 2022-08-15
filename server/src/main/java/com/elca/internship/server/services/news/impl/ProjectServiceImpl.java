package com.elca.internship.server.services.news.impl;

import com.elca.internship.server.exceptions.GroupLeaderNotExistedException;
import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.*;
import com.elca.internship.server.repositories.EmployeeRepository;
import com.elca.internship.server.repositories.GroupRepository;
import com.elca.internship.server.repositories.ProjectEmployeeRepository;
import com.elca.internship.server.repositories.ProjectRepository;
import com.elca.internship.server.services.news.ProjectService;
import com.elca.internship.server.validator.EmployeeValidator;
import com.elca.internship.server.validator.GroupValidator;
import com.elca.internship.server.validator.ProjectValidator;
import lombok.RequiredArgsConstructor;
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
    private final ProjectEmployeeRepository projectEmployeeRepository;
    private final EmployeeValidator employeeValidator;
    private final ProjectValidator projectValidator;
    private final GroupValidator groupValidator;


    @Override
    @Transactional
    public Project createNewProject(ProjectDto projectDto, List<String> listVisaEmployee) {

        // validate project number already existed
        projectValidator.validateProjectNumberAlreadyExisted(projectDto.getProjectNumber());
        // validate project member
        var listEmployeeExisted = employeeValidator.validateAndGetEmployeesExisted(listVisaEmployee);
        Group group;
        // validate project group
        var groupId = projectDto.getGroupId();
        if(groupId!=0){
            group = groupValidator.validateAndGetGroupIfExisted(groupId);
        }else{
            if(listEmployeeExisted.isEmpty()){
                throw new GroupLeaderNotExistedException("Group Leader must not be empty");
            }else{
                group = new Group(null, null,listEmployeeExisted.get(0), null);
                listEmployeeExisted.remove(0);
            }
        }
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
        // bun thit nuong chay
        // validate Project existed
        var projectUpdate = projectRepository.findProjectByIdCustom(projectDto.getId());

        // validate project member
        var listEmployeeExisted = employeeValidator.validateAndGetEmployeesExisted(listVisaEmployee);
        Group group;
        // validate project group
        var groupId = projectDto.getGroupId();
        if(groupId!=0){
            group = groupValidator.validateAndGetGroupIfExisted(groupId);
        }else{
            if(listEmployeeExisted.isEmpty()){
                throw new GroupLeaderNotExistedException("Group Leader must not be empty");
            }else{
                group = new Group(null, null,listEmployeeExisted.get(0), null);
                listEmployeeExisted.remove(0);
            }
        }

        projectUpdate.setGroup(group);

        // create setProjectEmployee

        var setProjectEmployee = new HashSet<ProjectEmployee>();
        listEmployeeExisted.forEach(employee -> {
            var projectEmployeeKey = new ProjectEmployeeKey(projectDto.getId(), employee.getId());
            setProjectEmployee.add(
                    new ProjectEmployee(projectEmployeeKey,
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
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        var isProjectExisted = projectRepository.existsById(id);
        if(isProjectExisted){
            projectRepository.deleteById(id);
        }
    }

    @Override
    public List<ProjectDto> getAllProjectsByCriteriaAndStatus(String criteria, Status status) {
        var listProjectSpecified = projectRepository.findProjectByCriteriaAndStatusCustom(criteria, status);

        return null;
    }

    @Override
    @Transactional
    public void deleteProjects(List<Long> ids){
        projectRepository.deleteAllByIdInBatch(ids);
    }

}
