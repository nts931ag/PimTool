package com.elca.internship.server.repositories.custom.impl;

import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.entity.QProject;
import com.elca.internship.server.models.entity.QProjectEmployee;
import com.elca.internship.server.repositories.custom.ProjectRepositoryCustom;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetchOne();
    }

    @Override
    public Project findProjectByIdCustom(Long projectId) {
        return new JPAQuery<Project>(entityManager)
                .from(QProject.project)
                .leftJoin(QProject.project.projectEmployee, QProjectEmployee.projectEmployee)
                .fetchJoin()
                .where(QProject.project.id.eq(projectId))
                .fetchFirst();
    }

    @Override
    public List<Project> findAllProjectByCriteriaAndStatusCustom(String criteria, Status status) {
        var expression = "%" + criteria + "%";
        return new JPAQuery<Project>(entityManager)
                .from(QProject.project)
                .where(
                        (QProject.project.name.like(expression)
                                .or(QProject.project.projectNumber.like(expression))
                                .or(QProject.project.customer.like(expression))
                                .and(QProject.project.status.eq(status))))
                .fetch();
    }

    @Override
    public List<Project> findAllProjectByStatusCustom(Status status) {
        return new JPAQuery<Project>(entityManager)
                .from(QProject.project)
                .where(QProject.project.status.eq(status))
                .fetch();
    }

    @Override
    public List<Project> findAllProjectByCriteriaCustom(String criteria) {
        var expression = "%" + criteria + "%";
        return new JPAQuery<Project>(entityManager)
                .from(QProject.project)
                .where(
                        (QProject.project.name.like(expression)
                                .or(QProject.project.projectNumber.like(expression))
                                .or(QProject.project.customer.like(expression))))
                                .fetch();
    }
}
