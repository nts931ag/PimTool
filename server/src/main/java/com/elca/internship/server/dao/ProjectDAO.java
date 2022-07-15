package com.elca.internship.server.dao;

import com.elca.internship.server.models.entity.Project;

import java.util.List;

public interface ProjectDAO {

    Project save(Project project);

    Project update(Long id, Project project);

    Long insert(Project project);

    Project findById(Long id);

    List<Project> findAll();

    void deleteById(long id);

    List<Project> findByProNameAndProStatus(String proName, String proStatus);

    List<Project> findByStatus(String proStatus);

    List<Project> findByProName(String proName);
}
