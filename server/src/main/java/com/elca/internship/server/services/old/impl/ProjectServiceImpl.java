//package com.elca.internship.server.services.impl;
//
//import com.elca.internship.server.dao.ProjectDAO;
//import com.elca.internship.server.dao.ProjectEmployeeDAO;
//import com.elca.internship.server.exceptions.EmployeeNotExistedException;
//import com.elca.internship.server.exceptions.GroupLeaderNotExistedException;
//import com.elca.internship.server.models.dto.ProjectDto;
//import com.elca.internship.server.models.entity.*;
//import com.elca.internship.server.exceptions.ProjectNumberAlreadyExistedException;
//import com.elca.internship.server.repositories.EmployeeRepository;
//import com.elca.internship.server.repositories.GroupRepository;
//import com.elca.internship.server.repositories.ProjectEmployeeRepository;
//import com.elca.internship.server.repositories.ProjectRepository;
//import com.elca.internship.server.services.ProjectService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Objects;
//
//
//@Service
//@RequiredArgsConstructor
//public class ProjectServiceImpl implements ProjectService {
//    private final ProjectDAO projectDAO;
//    private final ProjectEmployeeDAO projectEmployeeDAO;
//    private final ProjectRepository projectRepository;
//    private final EmployeeRepository employeeRepository;
//    private final GroupRepository groupRepository;
//    private final ProjectEmployeeRepository projectEmployeeRepository;
//
//    @Override
//    public Long createNewProject(Project project) throws ProjectNumberAlreadyExistedException {
//        return projectDAO.insert(project);
//    }
//
//
//
//    @Override
//    public Project getProjectById(Long id) throws EmptyResultDataAccessException {
//        return projectDAO.findById(id);
//    }
//
//    @Override
//    public int updateProject(Long id, Project project) {
//        return projectDAO.update(id, project);
//    }
//
//    @Override
//    public List<Project> getAllProject() {
//        return projectRepository.findAll();
////        return projectDAO.findAll();
//    }
//
//    @Override
//    public void deleteProject(Project project) {
//        projectDAO.deleteById(project.getId());
//    }
//
//    @Override
//    public List<Project> getProjectByCriteria(String proCriteria, String proStatus) {
//
//        if(proCriteria.isBlank() && proStatus.isBlank()){
//            return projectDAO.findAll();
//        }else{
//            if(proCriteria.isBlank()){
//                return projectDAO.findByStatus(proStatus);
//            }else{
//                if(proCriteria.matches("\\d+")){
//                    if(proStatus.isBlank()){
//                        return projectDAO.findByProNum(proCriteria);
//                    }else{
//                        return projectDAO.findByProNumAndProStatus(proCriteria, proStatus);
//                    }
//                }else{
//                    if(proStatus.isBlank()){
//                        return projectDAO.findByProCriteria(proCriteria);
//                    }else{
//                        return projectDAO.findByProCriteriaAndProStatus(proCriteria, proStatus);
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void deleteProjectsByIds(List<Long> Ids) {
//        projectEmployeeDAO.removeProjectEmployeeByProjectIds(Ids);
//        projectDAO.deleteByIds(Ids);
//    }
//
//    @Override
//    public Integer findProjectNumber(Integer proNum) throws EmptyResultDataAccessException{
//        return projectDAO.findProjectNumber(proNum);
//    }
//
//    @Override
//    public List<Project> getProjectByCriteriaWithPagination(String proCriteria, String proStatus, Integer pageNumber, Integer pageSize) {
//        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
//        var pageProject = projectDAO.findAllProjectSpecifiedWithPagination(proCriteria, proStatus, pageRequest);
//        return pageProject.get().toList();
//    }
//
//    @Override
//    public Integer getSizeOfResultSearch(String proCriteria, String proStatus) {
//        return projectDAO.calcSizeOfResultSearch(proCriteria, proStatus);
//    }
//
//    @Override
//    public boolean checkProjectNumberExisted(Integer projectNumber){
//        try{
//            var projectNumberFromDb = projectDAO.findProjectNumber(projectNumber);
//            return true;
//        }catch (EmptyResultDataAccessException erdae){
//            return false;
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void createProject(ProjectDto projectDto, List<String> listEmployeeVisa) {
//        var project = projectRepository.findByProjectNumber(projectDto.getProjectNumber());
//        project.ifPresent(p -> {
//            throw new ProjectNumberAlreadyExistedException(p.getProjectNumber());
//        });
//
//        var newProject = new Project(
//                null,
//                1,
//                null,
//                projectDto.getProjectNumber(),
//                projectDto.getName(),
//                projectDto.getCustomer(),
//                projectDto.getStatus(),
//                projectDto.getStartDate(),
//                projectDto.getEndDate(),
//                null
//        );
//
//        if(listEmployeeVisa.isEmpty() && projectDto.getGroupId() == 0){
//            throw new EmptyResultDataAccessException("Members can't be empty", 1);
//        }else{
//            var listEmployee = employeeRepository.findAllByVisaIn(listEmployeeVisa);
//
//            if(listEmployee.size() != listEmployeeVisa.size()){
//                var listVisaEmployeeExisted = listEmployee.stream().map(Employee::getVisa).toList();
//                var listVisaEmployeeNotExisted = listEmployeeVisa.stream().filter(v -> {
//                    if(!listVisaEmployeeExisted.contains(v)){
//                        return true;
//                    }
//                    return false;
//                }).toList();
//                throw new EmployeeNotExistedException(listVisaEmployeeNotExisted);
//            }
//
//            Group group;
//
//            if(projectDto.getGroupId() == 0){
//                group = groupRepository.save(new Group(null, 1, listEmployee.get(0), null));
//                listEmployee.remove(0);
//            }else{
//                group = groupRepository.findById(projectDto.getGroupId()).orElseThrow();
//            }
//
//            newProject.setGroup(group);
//
//            var setProjectEmployee = new LinkedHashSet<ProjectEmployee>();
//
//            listEmployee.forEach(e -> {
//                var projectEmployeeKey = new ProjectEmployeeKey(null, e.getId());
//                setProjectEmployee.add(new ProjectEmployee(projectEmployeeKey, newProject, e));
//            });
//
//            newProject.setProjectEmployee(setProjectEmployee);
//
//            var projectPersist = projectRepository.save(newProject);
//            group.addProjectToGroup(projectPersist);
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateProject(ProjectDto projectDto, List<String> listEmployeeVisa) {
//        var project = projectRepository.findById(projectDto.getId()).orElseThrow();
//
//        var infoProjectUpdate = new Project(
//                project.getId(),
//                project.getVersion(),
//                null,
//                project.getProjectNumber(),
//                projectDto.getName(),
//                projectDto.getCustomer(),
//                projectDto.getStatus(),
//                projectDto.getStartDate(),
//                projectDto.getEndDate(),
//                null
//        );
//
//        Group group;
//        List<Employee> listEmployee = null;
//
//        if(!project.getGroup().getId().equals(projectDto.getGroupId())){
//            if(listEmployeeVisa.isEmpty() && projectDto.getId() == 0){
//                throw new EmptyResultDataAccessException("Members can't be empty", 1);
//            }
//            listEmployee = employeeRepository.findAllByVisaIn(listEmployeeVisa);
//
//            if(listEmployee.size() != listEmployeeVisa.size()){
//                var listVisaEmployeeExisted = listEmployee.stream().map(Employee::getVisa).toList();
//                var listVisaEmployeeNotExisted = listEmployeeVisa.stream().filter(v -> {
//                    if(!listVisaEmployeeExisted.contains(v)){
//                        return true;
//                    }
//                    return false;
//                }).toList();
//                throw new EmployeeNotExistedException(listVisaEmployeeNotExisted);
//            }
//
//            if(projectDto.getGroupId() == 0){
//                group = groupRepository.save(new Group(null, 1, listEmployee.get(0), null));
//                listEmployee.remove(0);
//            }else{
//                group = groupRepository.findById(projectDto.getGroupId()).orElseThrow();
//            }
//            project.setGroup(group);
//        }
//        var setProjectEmployee = new LinkedHashSet<ProjectEmployee>();
//
//        if (listEmployee != null) {
//            listEmployee.forEach(e -> {
//                var projectEmployeeKey = new ProjectEmployeeKey(null, e.getId());
//                setProjectEmployee.add(new ProjectEmployee(projectEmployeeKey, project, e));
//            });
//        }
//
//
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateProjectWithEmployeeVisasTest(Project project, List<String> listEmployeeVisa) {
//
//        /*if(listEmployeeVisa.isEmpty()){
//            if(project.getGroupId() == 0){
//                throw new EmptyResultDataAccessException("Members can't be empty", 1);
//            }
//
//            try{
//                groupDAO.findById(project.getGroupId());
//            }catch (EmptyResultDataAccessException erdae){
//                throw new GroupNotExistedException(project.getGroupId());
//            }
//
////            projectDAO.insert(project);
//            projectDAO.update(project.getId(), project);
//            projectEmployeeDAO.deleteProjectEmployeeByProjectId(project.getId());
//
//        }else{
//            var mapVisaId = employeeDAO.getMapVisaIdByListVisa(listEmployeeVisa);
//            var listIdExisted = new ArrayList<Long>(mapVisaId.values());
//            var listVisaExisted = new ArrayList<String>(mapVisaId.keySet());
//
//            employeeValidator.validateEmployeesExisted(listEmployeeVisa, listVisaExisted);
//
//            if(project.getGroupId() == 0){
//                var idLeader = (Long) mapVisaId.get(listEmployeeVisa.get(0));
//                var newGroupId = groupDAO.insert(new Group(0, idLeader, 1));
//                listIdExisted.remove(idLeader);
//                project.setGroupId(newGroupId);
//            }else{
//                try{
//                    groupDAO.findById(project.getGroupId());
//                }catch (EmptyResultDataAccessException erdae){
//                    throw new GroupNotExistedException(project.getGroupId());
//                }
//            }
//
//            projectDAO.update(project.getId(), project);
//            projectEmployeeDAO.deleteEmployeesFromProjectEmployee(project.getId(), listIdExisted);
//            projectEmployeeDAO.saveNewEmployeesToProjectEmployee(project.getId(), listIdExisted);
//        }*/
//    }
//
//
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void createNewProjectWithEmployeeVisasTest(Project project, List<String> listEmployeeVisa) {
//
//
//        /*var projectNumberIsExisted = checkProjectNumberExisted(project.getProjectNumber());
//        if(projectNumberIsExisted){
//            throw new ProjectNumberAlreadyExistedException(project.getProjectNumber());
//        }
//
//        if(listEmployeeVisa.isEmpty()){
//            if(project.getGroupId() == 0){
//                // throws exception
//                throw new EmptyResultDataAccessException("Members can't be empty", 1);
//            }
//
//            try{
//                groupDAO.findById(project.getGroupId());
//            }catch (EmptyResultDataAccessException erdae){
//                throw new GroupNotExistedException(project.getGroupId());
//            }
//
//            projectDAO.insert(project);
//        }else{
//            var mapVisaId = employeeDAO.getMapVisaIdByListVisa(listEmployeeVisa);
//            var listIdExisted = new ArrayList<Long>(mapVisaId.values());
//            var listVisaExisted = new ArrayList<String>(mapVisaId.keySet());
//            employeeValidator.validateEmployeesExisted(listEmployeeVisa, listVisaExisted);
//            if(project.getGroupId() == 0){
//                var idLeader = (Long) mapVisaId.get(listEmployeeVisa.get(0));
//                var newGroupId = groupDAO.insert(new Group(0, idLeader, 1));
//                listIdExisted.remove(idLeader);
//                project.setGroupId(newGroupId);
//            }else{
//                try{
//                    groupDAO.findById(project.getGroupId());
//                }catch (EmptyResultDataAccessException erdae){
//                    throw new GroupNotExistedException(project.getGroupId());
//                }
//            }
//
//            var newProjectId = projectDAO.insert(project);
//            projectEmployeeDAO.saveProjectEmployee(newProjectId, listIdExisted);
//        }*/
//    }
//
//}
