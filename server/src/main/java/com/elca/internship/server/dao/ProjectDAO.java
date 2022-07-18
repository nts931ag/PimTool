package com.elca.internship.server.dao;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistsException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface ProjectDAO {

    Project save(Project project);

    Project update(Long id, Project project);

    Long insert(Project project) throws ProjectNumberAlreadyExistsException;

    Project findById(Long id);

    List<Project> findAll();

    void deleteById(long id);

    List<Project> findByProNameAndProStatus(String proName, String proStatus);

    List<Project> findByProCriteriaAndProStatus(String proCriteria, String proStatus);

    List<Project> findByStatus(String proStatus);

    List<Project> findByProName(String proName);

    List<Project> findByProNum(String proCriteria);

    List<Project> findByProNumAndProStatus(String proCriteria, String proStatus);

    List<Project> findByProCriteria(String proCriteria);
}
