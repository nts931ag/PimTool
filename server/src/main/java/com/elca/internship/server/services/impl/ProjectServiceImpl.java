package com.elca.internship.server.services.impl;

import com.elca.internship.server.dao.ProjectDAO;
import com.elca.internship.server.dao.ProjectEmployeeDAO;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.exceptions.ProjectNumberAlreadyExistedException;
import com.elca.internship.server.repositories.ProjectRepository;
import com.elca.internship.server.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDAO projectDAO;
    private final ProjectEmployeeDAO projectEmployeeDAO;
    private final ProjectRepository projectRepository;

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
        return projectRepository.findAll();
//        return projectDAO.findAll();
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
    }

    @Override
    public Integer getSizeOfResultSearch(String proCriteria, String proStatus) {
        return projectDAO.calcSizeOfResultSearch(proCriteria, proStatus);
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
    @Transactional(rollbackFor = Exception.class)
    public void updateProjectWithEmployeeVisasTest(Project project, List<String> listEmployeeVisa) {

        /*if(listEmployeeVisa.isEmpty()){
            if(project.getGroupId() == 0){
                throw new EmptyResultDataAccessException("Members can't be empty", 1);
            }

            try{
                groupDAO.findById(project.getGroupId());
            }catch (EmptyResultDataAccessException erdae){
                throw new GroupNotExistedException(project.getGroupId());
            }

//            projectDAO.insert(project);
            projectDAO.update(project.getId(), project);
            projectEmployeeDAO.deleteProjectEmployeeByProjectId(project.getId());

        }else{
            var mapVisaId = employeeDAO.getMapVisaIdByListVisa(listEmployeeVisa);
            var listIdExisted = new ArrayList<Long>(mapVisaId.values());
            var listVisaExisted = new ArrayList<String>(mapVisaId.keySet());

            employeeValidator.validateEmployeesExisted(listEmployeeVisa, listVisaExisted);

            if(project.getGroupId() == 0){
                var idLeader = (Long) mapVisaId.get(listEmployeeVisa.get(0));
                var newGroupId = groupDAO.insert(new Group(0, idLeader, 1));
                listIdExisted.remove(idLeader);
                project.setGroupId(newGroupId);
            }else{
                try{
                    groupDAO.findById(project.getGroupId());
                }catch (EmptyResultDataAccessException erdae){
                    throw new GroupNotExistedException(project.getGroupId());
                }
            }

            projectDAO.update(project.getId(), project);
            projectEmployeeDAO.deleteEmployeesFromProjectEmployee(project.getId(), listIdExisted);
            projectEmployeeDAO.saveNewEmployeesToProjectEmployee(project.getId(), listIdExisted);
        }*/
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createNewProjectWithEmployeeVisasTest(Project project, List<String> listEmployeeVisa) {

        /*var projectNumberIsExisted = checkProjectNumberExisted(project.getProjectNumber());
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
                var idLeader = (Long) mapVisaId.get(listEmployeeVisa.get(0));
                var newGroupId = groupDAO.insert(new Group(0, idLeader, 1));
                listIdExisted.remove(idLeader);
                project.setGroupId(newGroupId);
            }else{
                try{
                    groupDAO.findById(project.getGroupId());
                }catch (EmptyResultDataAccessException erdae){
                    throw new GroupNotExistedException(project.getGroupId());
                }
            }

            var newProjectId = projectDAO.insert(project);
            projectEmployeeDAO.saveProjectEmployee(newProjectId, listIdExisted);
        }*/
    }

}
