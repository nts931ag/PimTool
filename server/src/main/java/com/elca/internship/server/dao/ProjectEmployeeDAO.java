package com.elca.internship.server.dao;

import java.util.List;

public interface ProjectEmployeeDAO {

    void saveProjectEmployee(Long idProject, List<Long> listMember);
}