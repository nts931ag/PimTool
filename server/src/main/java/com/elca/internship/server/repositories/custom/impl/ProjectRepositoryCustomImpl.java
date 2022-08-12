package com.elca.internship.server.repositories.custom.impl;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.entity.QProject;
import com.elca.internship.server.models.entity.QProjectEmployee;
import com.elca.internship.server.repositories.custom.ProjectRepositoryCustom;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Project findProjectByProjectNumber(Integer projectNumber) {
        return new JPAQuery<Project>(entityManager)
                .from(QProject.project)
                .innerJoin(QProject.project.projectEmployee, QProjectEmployee.projectEmployee)
                .fetchJoin()
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetchOne();
    }
}
