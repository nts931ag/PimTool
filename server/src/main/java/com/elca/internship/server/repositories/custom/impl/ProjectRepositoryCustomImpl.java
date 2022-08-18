package com.elca.internship.server.repositories.custom.impl;

import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.entity.QGroup;
import com.elca.internship.server.models.entity.QProject;
import com.elca.internship.server.models.entity.QProjectEmployee;
import com.elca.internship.server.repositories.ProjectRepository;
import com.elca.internship.server.repositories.custom.ProjectRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Project findProjectByProjectNumberCustom(Integer projectNumber) {
        return new JPAQuery<Project>(entityManager)
                .from(QProject.project)
                .leftJoin(QProject.project.projectEmployee, QProjectEmployee.projectEmployee)
                .fetchJoin()
                .leftJoin(QProject.project.group, QGroup.group)
                .fetchJoin()
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetchOne();
    }

    @Override
    public Project findProjectByIdCustom(Long projectId) {
        return new JPAQuery<Project>(entityManager)
                .from(QProject.project)
                .leftJoin(QProject.project.projectEmployee, QProjectEmployee.projectEmployee)
                .fetchJoin()
                .leftJoin(QProject.project.group, QGroup.group)
                .fetchJoin()
                .where(QProject.project.id.eq(projectId))
                .fetchFirst();
    }

    @Override
    public List<Project> findAllCustom() {
        return new JPAQuery<Project>(entityManager)
                .from(QProject.project)
                .leftJoin(QProject.project.group, QGroup.group)
                .fetchJoin()
                .fetch();
    }

    @Override
    public Page<Project> findAllProjectByCriteriaAndStatusWithPaginationCustom(String criteria, Status status, Pageable page) {
        var querydsl = new Querydsl(entityManager, (new PathBuilderFactory()).create(Project.class));
        var root = QProject.project;

        var predicate = root.projectNumber.like(criteria)
                .or(root.name.containsIgnoreCase(criteria))
                .or(root.customer.containsIgnoreCase(criteria));

        if(status!= null){
            predicate = predicate.and(root.status.eq(status));
        }

        var query = new JPAQuery<Project>(entityManager)
                .from(QProject.project)
                .leftJoin(QProject.project.group, QGroup.group)
                .fetchJoin()
                .where(predicate);

        var applyPagination = querydsl.applyPagination(page, query);
        var totalElements = applyPagination.fetchCount();
        var result = applyPagination.fetch();

        return new PageImpl<>(result, page, totalElements);
    }
}
