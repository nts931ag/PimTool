package com.elca.internship.server.services.impl;

import com.elca.internship.server.dao.ProjectEmployeeDAO;
import com.elca.internship.server.services.ProjectEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectEmployeeServiceImpl implements ProjectEmployeeService {

    private final ProjectEmployeeDAO projectEmployeeDao;
    @Override
    public void saveAllEmployeeToNewProject(Long newProjectId, List<Long> listEmployee) {
        projectEmployeeDao.saveProjectEmployee(newProjectId, listEmployee);
    }

    @Override
    public void removeProjectEmployeeByProjectId(Long id) {
        projectEmployeeDao.deleteProjectEmployeeByProjectId(id);
    }

    @Override
    public List<String> getAllEmployeeVisasByProjectId(Long projectId) {
        return projectEmployeeDao.findAllEmployeeVisaByProjectId(projectId);
    }

    @Override
    public List<String> getAllVisaAndNameOfEmployeeByProjectId(Long projectId) {

        return projectEmployeeDao.findVisaAndNameByProjectId(projectId);
    }
}
