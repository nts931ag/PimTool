package com.elca.internship.server.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeNotExistedException extends RuntimeException{
        public List<String> listVisaNotExisted;

        public EmployeeNotExistedException(List<String> listVisaNotExisted){
            super(String.format("List visa not existed: %s!", listVisaNotExisted));
            this.listVisaNotExisted = listVisaNotExisted;
        }
}
