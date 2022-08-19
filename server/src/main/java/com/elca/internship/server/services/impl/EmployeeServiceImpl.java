package com.elca.internship.server.services.impl;

import com.elca.internship.server.models.entity.Employee;
import com.elca.internship.server.repositories.EmployeeRepository;
import com.elca.internship.server.services.EmployeeService;
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

    @Override
    public List<Employee> getAllEmployeeByProjectId(Long projectId) {
        return employeeRepository.findAllByProjectId(projectId);
    }
}
