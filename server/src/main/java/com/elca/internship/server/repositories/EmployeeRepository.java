package com.elca.internship.server.repositories;

import com.elca.internship.server.models.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee> {
    List<Employee> findAllByVisaIn(List<String> listVisa);

    @Query(value = "SELECT e FROM Employee e JOIN ProjectEmployee pe ON e.id = pe.id.employeeId WHERE pe.id.projectId = :id")
    List<Employee> findAllByProjectId(@Param("id") Long projectId);
}
