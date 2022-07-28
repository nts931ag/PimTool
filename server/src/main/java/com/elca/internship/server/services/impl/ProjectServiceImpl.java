package com.elca.internship.server.services.impl;

import com.elca.internship.server.dao.EmployeeDAO;
import com.elca.internship.server.dao.GroupDAO;
import com.elca.internship.server.dao.ProjectDAO;
import com.elca.internship.server.dao.ProjectEmployeeDAO;
import com.elca.internship.server.models.entity.Group;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.EmployeeNotExistedException;
import com.elca.internship.server.models.exceptions.GroupNotExistedException;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistedException;
import com.elca.internship.server.services.ProjectService;
import com.elca.internship.server.validator.EmployeeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDAO projectDAO;
    private final EmployeeDAO employeeDAO;
    private final GroupDAO groupDAO;
    private final ProjectEmployeeDAO projectEmployeeDAO;
    private final EmployeeValidator employeeValidator;

    @Override
    public Long createNewProject(Project project) throws ProjectNumberAlreadyExistedException {
        return projectDAO.insert(project);
    }

    @Override
    public Project getProjectById(Long id) throws EmptyResultDataAccessException {
        return projectDAO.findById(id);
    }

    @Override
    public int updateProject(Long id, Project project) {
        return projectDAO.update(id, project);
    }

    @Override
    public List<Project> getAllProject() {
        return projectDAO.findAll();
    }

    @Override
    public void deleteProject(Project project) {
        projectDAO.deleteById(project.getId());
    }

    @Override
    public List<Project> getProjectByCriteria(String proCriteria, String proStatus) {

        if(proCriteria.isBlank() && proStatus.isBlank()){
            return projectDAO.findAll();
        }else{
            if(proCriteria.isBlank()){
                return projectDAO.findByStatus(proStatus);
            }else{
                if(proCriteria.matches("\\d+")){
                    if(proStatus.isBlank()){
                        return projectDAO.findByProNum(proCriteria);
                    }else{
                        return projectDAO.findByProNumAndProStatus(proCriteria, proStatus);
                    }
                }else{
                    if(proStatus.isBlank()){
                        return projectDAO.findByProCriteria(proCriteria);
                    }else{
                        return projectDAO.findByProCriteriaAndProStatus(proCriteria, proStatus);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProjectWithListEmployeeVisa(Project project, List<String> listEmployeeVisa) throws EmployeeNotExistedException, GroupNotExistedException {
        // check all visa existed
        var mapVisaId = employeeDAO.getMapVisaIdByListVisa(listEmployeeVisa);
        var listId = new ArrayList<Long>(mapVisaId.values());
        var listVisaExisted = new ArrayList<String>(mapVisaId.keySet());
        employeeValidator.validateEmployeesExisted(listEmployeeVisa, listVisaExisted);
        // check group is existed?
        if(project.getGroupId() == 0){
            var newGroupId = groupDAO.insert(new Group(0,listId.get(0),1));
            project.setGroupId(newGroupId);
        }else{
            try{
                groupDAO.findById(project.getGroupId());
            }catch (EmptyResultDataAccessException e){
                throw new GroupNotExistedException(project.getGroupId());
            }
        }
        // update project
        projectDAO.update(project.getId(), project);
        projectEmployeeDAO.deleteEmployeesFromProjectEmployee(project.getId(), listId);
        projectEmployeeDAO.saveNewEmployeesToProjectEmployee(project.getId(), listId);
//        projectEmployeeDAO.saveProjectEmployee(project.getId(), listId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createNewProjectWithEmployeeVisas(Project project, List<String> listEmployeeVisa) throws ProjectNumberAlreadyExistedException, EmployeeNotExistedException, GroupNotExistedException {
        // check all visa existed
        var mapVisaId = employeeDAO.getMapVisaIdByListVisa(listEmployeeVisa);
        var listId = new ArrayList<Long>(mapVisaId.values());
        var listVisaExisted = new ArrayList<String>(mapVisaId.keySet());
        employeeValidator.validateEmployeesExisted(listEmployeeVisa, listVisaExisted);
        // check group is existed?
        if(project.getGroupId() == 0){
            var newGroupId = groupDAO.insert(new Group(0,listId.get(0),1));
            project.setGroupId(newGroupId);
        }else{
            try{
                groupDAO.findById(project.getGroupId());
            }catch (EmptyResultDataAccessException e){
                throw new GroupNotExistedException(project.getGroupId());
            }
        }
        // create project
        var newProjectId = projectDAO.insert(project);
        projectEmployeeDAO.saveProjectEmployee(newProjectId, listId);
    }

    @Override
    public void deleteProjectsByIds(List<Long> Ids) {
        projectEmployeeDAO.removeProjectEmployeeByProjectIds(Ids);
        projectDAO.deleteByIds(Ids);
    }

    @Override
    public Integer findProjectNumber(Integer proNum) throws EmptyResultDataAccessException{
        return projectDAO.findProjectNumber(proNum);
    }

    @Override
    public List<Project> getProjectByCriteriaWithPagination(String proCriteria, String proStatus, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        var pageProject = projectDAO.findAllProjectSpecifiedWithPagination(proCriteria, proStatus, pageRequest);
        return pageProject.get().toList();
//        if(proCriteria.isBlank() && proStatus.isBlank()){
//            var pageProject = projectDAO.findAllProjectSpecifiedWithPagination("", "", pageRequest);
//            return pageProject.get().toList();
//        }else{
//            var pageProject = projectDAO.findAllProjectSpecifiedWithPagination(proCriteria, proStatus, pageRequest);
//            return pageProject.get().toList();
//        }
    }
    @Override
    public boolean checkProjectNumberExisted(Integer projectNumber){
        try{
            var projectNumberFromDb = projectDAO.findProjectNumber(projectNumber);
            return true;
        }catch (EmptyResultDataAccessException erdae){
            return false;
        }
    }

    @Override
    public void createNewProjectWithEmployeeVisasTest(Project project, List<String> listEmployeeVisa) {

        var projectNumberIsExisted = checkProjectNumberExisted(project.getProjectNumber());
        if(projectNumberIsExisted){
            throw new ProjectNumberAlreadyExistedException(project.getProjectNumber());
        }

        if(listEmployeeVisa.isEmpty()){
            if(project.getGroupId() == 0){
                // throws exception
                throw new EmptyResultDataAccessException("Members can't be empty", 1);
            }

            try{
                groupDAO.findById(project.getGroupId());
            }catch (EmptyResultDataAccessException erdae){
                throw new GroupNotExistedException(project.getGroupId());
            }

            projectDAO.insert(project);
        }else{
            var mapVisaId = employeeDAO.getMapVisaIdByListVisa(listEmployeeVisa);
            var listIdExisted = new ArrayList<Long>(mapVisaId.values());
            var listVisaExisted = new ArrayList<String>(mapVisaId.keySet());

            employeeValidator.validateEmployeesExisted(listEmployeeVisa, listVisaExisted);

            if(project.getGroupId() == 0){
                var idLeader = listIdExisted.get(0);
                var newGroupId = groupDAO.insert(new Group(0, idLeader, 1));
                listIdExisted.remove(0);
                listVisaExisted.remove(0);
                project.setGroupId(newGroupId);
            }

            try{
                groupDAO.findById(project.getGroupId());
            }catch (EmptyResultDataAccessException erdae){
                throw new GroupNotExistedException(project.getGroupId());
            }


            var newProjectId = projectDAO.insert(project);
            projectEmployeeDAO.saveProjectEmployee(newProjectId, listIdExisted);
        }
    }

}
