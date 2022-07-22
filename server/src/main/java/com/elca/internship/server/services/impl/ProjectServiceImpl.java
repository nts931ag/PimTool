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
import com.elca.internship.server.utils.Response;
import com.elca.internship.server.validator.EmployeeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public Long findProjectNumber(Long proNum) throws EmptyResultDataAccessException{
        return projectDAO.findProjectNumber(proNum);
    }

}
