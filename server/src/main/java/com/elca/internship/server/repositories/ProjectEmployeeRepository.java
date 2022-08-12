package com.elca.internship.server.repositories;

import com.elca.internship.server.models.entity.ProjectEmployee;
import com.elca.internship.server.models.entity.ProjectEmployeeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, ProjectEmployeeKey> {
}
