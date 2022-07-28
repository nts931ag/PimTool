package com.elca.internship.server.models.exceptions;

import java.util.List;

public class EmployeeNotExistedException extends RuntimeException{
        public List<String> listVisaNotExisted;

        public EmployeeNotExistedException(List<String> listVisaNotExisted){
            super(String.format("List visa not existed: %s!", listVisaNotExisted));
            this.listVisaNotExisted = listVisaNotExisted;
        }
}
