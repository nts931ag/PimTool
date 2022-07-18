package com.elca.internship.server.models.exceptions;

public class ProjectNumberAlreadyExistsException extends Exception{
    public ProjectNumberAlreadyExistsException(Integer projectNumber) {
        super(String.format("Project number %s is already existed.", projectNumber));
    }
}
