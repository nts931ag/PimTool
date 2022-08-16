package com.elca.internship.server.services.news.impl;

import com.elca.internship.server.models.entity.Employee;
import com.elca.internship.server.repositories.EmployeeRepository;
import com.elca.internship.server.repositories.ProjectEmployeeRepository;
import com.elca.internship.server.services.news.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }
}
