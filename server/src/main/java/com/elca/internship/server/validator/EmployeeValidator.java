package com.elca.internship.server.validator;

import com.elca.internship.server.models.exceptions.EmployeeNotExistedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EmployeeValidator {
    public void validateEmployeesExisted(List<String> listVisa, List<String> listVisaExisted) throws EmployeeNotExistedException {
        if(listVisa.size()!=listVisaExisted.size()){
            var listVisaNotExisted = listVisa;
            listVisaNotExisted.removeAll(listVisaExisted);
            throw new EmployeeNotExistedException(listVisaNotExisted);
        }
    }
}
