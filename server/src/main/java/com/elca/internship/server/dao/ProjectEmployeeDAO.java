package com.elca.internship.server.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ProjectEmployeeDAO {

    void saveProjectEmployee(Long idProject, List<Long> listMember);

    void deleteProjectEmployeeByProjectId(Long id);

    List<String> findAllEmployeeVisaByProjectId(Long projectId);

    void deleteEmployeesFromProjectEmployee(long id, List<Long> values);

    void saveNewEmployeesToProjectEmployee(long id, ArrayList<Long> listId);
}
