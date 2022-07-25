package com.elca.internship.server.dao;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectDAO {

    Project save(Project project);

    int update(Long id, Project project);

    Long insert(Project project) throws ProjectNumberAlreadyExistedException;

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

    void deleteByIds(List<Long> ids);

    Long findProjectNumber(Long proNum);

    int count();

    Page<Project> findAllProjectWithPagination(Pageable pageable);

    Page<Project> findAllProjectSpecifiedWithPagination(String proCriteria, String proStatus, Pageable pageable);
}
