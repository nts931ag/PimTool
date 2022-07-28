package com.elca.internship.server.test;

import com.elca.internship.server.models.exceptions.EmployeeNotExistedException;
import com.elca.internship.server.models.exceptions.GroupNotExistedException;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GroupNotExistedException.class)
    public ResponseEntity<ErrorResponse> handleGroupNotExistedException(GroupNotExistedException groupNotExistedException){
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), groupNotExistedException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(EmployeeNotExistedException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotExistedException(EmployeeNotExistedException employeeNotExistedException){
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), employeeNotExistedException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeEmptyCreateNewProjectWithNewGroup(EmptyResultDataAccessException emptyResultDataAccessException){
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), emptyResultDataAccessException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ProjectNumberAlreadyExistedException.class)
    public ResponseEntity<ErrorResponse> handleProjectNumberAlreadyExistedException(ProjectNumberAlreadyExistedException projectNumberAlreadyExistedException){
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), projectNumberAlreadyExistedException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
