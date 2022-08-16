package com.elca.internship.server.adapter;

import com.elca.internship.server.mappers.EmployeeMapperCustom;
import com.elca.internship.server.models.dto.EmployeeDto;
import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.Employee;
import com.elca.internship.server.services.news.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeAdapter {
    private final EmployeeService employeeService;
    private final EmployeeMapperCustom employeeMapperCustom;
    public List<EmployeeDto> getAllEmployees() {
        return employeeMapperCustom.listEntityToListDto(employeeService.getAllEmployee());
    }

    public List<EmployeeDto> getAllEmployeesByProjectId(Long projectId) {
        return employeeMapperCustom.listEntityToListDto(employeeService.getAllEmployeeByProjectId(projectId));
    }
}
