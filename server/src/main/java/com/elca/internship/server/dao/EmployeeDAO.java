package com.elca.internship.server.dao;

import com.elca.internship.server.models.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Long> findIdBylistVisa(List<String> listVisa);

    List<Employee> findAll();
}
