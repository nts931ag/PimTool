package com.elca.internship.server.validator;

import com.elca.internship.server.exceptions.EmployeeNotExistedException;
import com.elca.internship.server.models.entity.Employee;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeValidator {
    public void validateEmployeesExisted(List<Employee> listEmployee, List<String> listVisaValidate) throws EmployeeNotExistedException {
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
    }

}
