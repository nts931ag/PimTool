package com.elca.internship.server.services.impl;

import com.elca.internship.server.dao.EmployeeDAO;
import com.elca.internship.server.dao.GroupDAO;
import com.elca.internship.server.dao.ProjectDAO;
import com.elca.internship.server.dao.ProjectEmployeeDAO;
import com.elca.internship.server.models.entity.Group;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistsException;
import com.elca.internship.server.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDAO projectDAO;
    private final EmployeeDAO employeeDAO;
    private final GroupDAO groupDAO;
    private final ProjectEmployeeDAO projectEmployeeDAO;

    @Override
    public Long createNewProject(Project project) throws ProjectNumberAlreadyExistsException {
        return projectDAO.insert(project);
    }

    @Override
    public Project getProject(Long id) {
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
    public void updateProjectWithListEmployeeVisa(Project project, List listEmployeeVisa) {

        // check all visa existed
        var mapVisaId = employeeDAO.getMapVisaIdByListVisa(listEmployeeVisa);
        var listId = new ArrayList<Long>(mapVisaId.values());
        if(mapVisaId.size() != listEmployeeVisa.size()){
            // throw exception employee not existed
//            throw new EmployeeNotExistedException();
        }
        // check group is existed?
        if(project.getGroupId() == 0){
            var newGroupId = groupDAO.insert(new Group(0,listId.get(0),1));
            project.setGroupId(newGroupId);
        }else{
            var newGroupId = groupDAO.findById(project.getGroupId());
            if(newGroupId == null){
                // throw exception group not existed
//                throw new GroupNotExistedException();
            }
        }
        // update project
        System.out.println(project.getId());
        projectDAO.update(project.getId(), project);
        projectEmployeeDAO.deleteEmployeesFromProjectEmployee(project.getId(), listId);
        projectEmployeeDAO.saveNewEmployeesToProjectEmployee(project.getId(), listId);
//        projectEmployeeDAO.saveProjectEmployee(project.getId(), listId);
    }

}
