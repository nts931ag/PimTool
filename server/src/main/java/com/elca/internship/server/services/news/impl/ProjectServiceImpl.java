package com.elca.internship.server.services.news.impl;

import com.elca.internship.server.exceptions.GroupWithoutGroupLeaderException;
import com.elca.internship.server.mappers.ProjectMapperCustom;
import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.*;
import com.elca.internship.server.repositories.ProjectRepository;
import com.elca.internship.server.services.news.ProjectService;
import com.elca.internship.server.validator.EmployeeValidator;
import com.elca.internship.server.validator.GroupValidator;
import com.elca.internship.server.validator.ProjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapperCustom projectMapperCustom;
    private final EmployeeValidator employeeValidator;
    private final ProjectValidator projectValidator;
    private final GroupValidator groupValidator;

    private HashSet<ProjectEmployee> createSetProjectEmployee(Project project, List<Employee> listEmployeeExisted){

        var setProjectEmployee = new HashSet<ProjectEmployee>();
        listEmployeeExisted.forEach(employee -> {
            var projectEmployeeKey = new ProjectEmployeeKey(project.getId(), employee.getId());
            var projectEmployee = new ProjectEmployee(projectEmployeeKey, project, employee);
            setProjectEmployee.add(projectEmployee);
        });
        return setProjectEmployee;
    }


    @Override
    @Transactional
    public Project createNewProject(ProjectDto projectDto, List<String> listVisaEmployee) {

        // validate project number already existed
        projectValidator.validateProjectNumberAlreadyExisted(projectDto.getProjectNumber());
        // validate project member
        var listEmployeeExisted = employeeValidator.validateAndGetEmployeesIfExisted(listVisaEmployee);
        Group group;
        // validate project group
        var groupId = projectDto.getGroupId();
        if(groupId!=0){
            group = groupValidator.validateAndGetGroupIfExisted(groupId);
        }else{
            if(listEmployeeExisted.isEmpty()){
                throw new GroupWithoutGroupLeaderException("Group don't have group leader");
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
        var setProjectEmployee = createSetProjectEmployee(newProject, listEmployeeExisted);
        newProject.setProjectEmployee(setProjectEmployee);
        // save new project
        return projectRepository.save(newProject);
    }

    @Override
    public Project updateProject(ProjectDto projectDto, List<String> listVisaEmployee) {
        // bun thit nuong chay
        // validate Project existed
        var projectUpdate = projectValidator.validateProjectIfExisted(projectDto.getId());
        // validate project member
        var listEmployeeExisted = employeeValidator.validateAndGetEmployeesIfExisted(listVisaEmployee);
        Group group;
        // validate project group
        var groupId = projectDto.getGroupId();
        if(groupId!=0){
            group = groupValidator.validateAndGetGroupIfExisted(groupId);
        }else{
            if(listEmployeeExisted.isEmpty()){
                throw new GroupWithoutGroupLeaderException("Group Leader must not be empty");
            }else{
                group = new Group(null, null,listEmployeeExisted.get(0), null);
                listEmployeeExisted.remove(0);
            }
        }

        projectUpdate.setGroup(group);

        // create setProjectEmployee
        var setProjectEmployee = createSetProjectEmployee(projectUpdate, listEmployeeExisted);

        projectUpdate.setCustomer(projectDto.getCustomer());
        projectUpdate.setEndDate(projectDto.getEndDate());
        projectUpdate.setStartDate(projectDto.getStartDate());
        projectUpdate.setStatus(projectDto.getStatus());
        projectUpdate.setName(projectDto.getName());
        projectUpdate.getProjectEmployee().clear();
        projectUpdate.getProjectEmployee().addAll(setProjectEmployee);
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
    public Page<Project> getAllProjectByCriteriaAndStatusWithPagination(String criteria, Status status, Pageable pageable) {
        return projectRepository.findAllProjectByCriteriaAndStatusWithPaginationCustom(criteria, status, pageable);

    }

    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAllCustom();
    }

    @Override
    public Project getProjectByProjectNumber(Integer projectNumber) {
        return projectRepository.findProjectByProjectNumberCustom(projectNumber);
    }

    @Override
    @Transactional
    public void deleteProjects(List<Long> ids){
        projectRepository.deleteAllById(ids);
//        projectRepository.deleteAllByIdInBatch(ids);
    }
}
