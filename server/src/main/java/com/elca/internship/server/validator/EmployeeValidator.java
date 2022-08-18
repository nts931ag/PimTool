package com.elca.internship.server.validator;

import com.elca.internship.server.exceptions.EmployeeNotExistedException;
import com.elca.internship.server.models.entity.Employee;
import com.elca.internship.server.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeValidator {

    private final EmployeeRepository employeeRepository;

    public List<Employee> validateAndGetEmployeesIfExisted(List<String> listVisaValidate) throws EmployeeNotExistedException {
        List<Employee> listEmployee = employeeRepository.findAllByVisaIn(listVisaValidate);
        if(listEmployee.size() != listVisaValidate.size()){
           var listVisaValid = listEmployee.stream().map(Employee::getVisa).collect(Collectors.toList());
           var listVisaInvalid = listVisaValidate.stream().filter(s -> {
               if(listVisaValid.contains(s)){
                   return false;
               }
               return true;
           }).toList();
           throw new EmployeeNotExistedException(listVisaInvalid);
        }
        return listEmployee;
    }

}
