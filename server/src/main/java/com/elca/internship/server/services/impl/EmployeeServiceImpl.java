package com.elca.internship.server.services.impl;

import com.elca.internship.server.dao.EmployeeDAO;
import com.elca.internship.server.models.entity.Employee;
import com.elca.internship.server.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDao;


    @Override
    public List<Long> getIdsByListVisa(List<String> listVisa) {
        return employeeDao.findIdBylistVisa(listVisa);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.findAll();
    }
}
