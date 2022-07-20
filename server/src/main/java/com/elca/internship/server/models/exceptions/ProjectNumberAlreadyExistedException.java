package com.elca.internship.server.models.exceptions;

public class ProjectNumberAlreadyExistedException extends Exception{
    public ProjectNumberAlreadyExistedException(Integer projectNumber) {
        super(String.format("Project number %s is already existed.", projectNumber));
    }
}
