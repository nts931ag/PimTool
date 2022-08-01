package com.elca.internship.server.services;

import java.util.List;

public interface ProjectEmployeeService {

    void saveAllEmployeeToNewProject(Long newProjectId, List<Long> listEmployee);

    void removeProjectEmployeeByProjectId(Long id);

    List<String> getAllEmployeeVisasByProjectId(Long projectId);

    List<String> getAllVisaAndNameOfEmployeeByProjectId(Long projectId);
}
