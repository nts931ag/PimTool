package com.elca.internship.server.exceptions;

import lombok.Data;

@Data
public class ProjectNumberAlreadyExistedException extends RuntimeException{
    private Integer projectNumber;
    public ProjectNumberAlreadyExistedException(Integer projectNumber) {
        super(String.format("Project number %s is already existed!", projectNumber));
        this.projectNumber = projectNumber;
    }
}
