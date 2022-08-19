package com.elca.internship.server.repositories;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.repositories.custom.ProjectRepositoryCustom;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long>, QuerydslPredicateExecutor<Project>, ProjectRepositoryCustom {
    Optional<Project> findByProjectNumber(Integer projectNumber);


}
