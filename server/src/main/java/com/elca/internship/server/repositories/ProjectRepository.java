package com.elca.internship.server.repositories;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.repositories.custom.ProjectRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, QuerydslPredicateExecutor<Project>, ProjectRepositoryCustom {
    Optional<Project> findByProjectNumber(Integer projectNumber);

}
