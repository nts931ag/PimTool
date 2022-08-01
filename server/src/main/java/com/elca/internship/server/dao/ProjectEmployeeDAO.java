package com.elca.internship.server.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ProjectEmployeeDAO {

    void saveProjectEmployee(Long idProject, List<Long> listMember);

    void deleteProjectEmployeeByProjectId(Long id);

    List<String> findAllEmployeeVisaByProjectId(Long projectId);

    void deleteEmployeesFromProjectEmployee(Long id, List<Long> values);

    void saveNewEmployeesToProjectEmployee(Long id, ArrayList<Long> listId);

    void removeProjectEmployeeByProjectIds(List<Long> ids);

    List<String> findVisaAndNameByProjectId(Long projectId);
}
