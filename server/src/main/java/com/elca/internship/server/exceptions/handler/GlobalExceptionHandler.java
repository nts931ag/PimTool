package com.elca.internship.server.exceptions.handler;

import com.elca.internship.server.exceptions.EmployeeNotExistedException;
import com.elca.internship.server.exceptions.GroupNotExistedException;
import com.elca.internship.server.exceptions.ProjectNotExistedException;
import com.elca.internship.server.exceptions.ProjectNumberAlreadyExistedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLTransientConnectionException;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GroupNotExistedException.class)
    public ResponseEntity<ErrorResponse> handleGroupNotExistedException(GroupNotExistedException groupNotExistedException){
        var errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.toString(),
                groupNotExistedException.getMessage(),
                ErrorResponseKey.ERROR_RESPONSE_PROJECT_GROUP_NOT_EXISTED,
                groupNotExistedException.getGroupId().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(EmployeeNotExistedException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotExistedException(EmployeeNotExistedException employeeNotExistedException){
        var listEmployeeNotExisted = employeeNotExistedException.getListVisaNotExisted().toString();
        listEmployeeNotExisted.replace("[", "");
        listEmployeeNotExisted.replace("]","");
        var errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.toString(),
                employeeNotExistedException.getMessage(),
                ErrorResponseKey.ERROR_RESPONSE_PROJECT_EMPLOYEE_NOT_EXISTED,
                listEmployeeNotExisted);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeEmptyCreateNewProjectWithNewGroup(EmptyResultDataAccessException emptyResultDataAccessException){
        var errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.toString(),
                emptyResultDataAccessException.getMessage(),
                ErrorResponseKey.ERROR_RESPONSE_PROJECT_MEMBER_NOT_EMPTY,
                null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ProjectNumberAlreadyExistedException.class)
    public ResponseEntity<ErrorResponse> handleProjectNumberAlreadyExistedException(ProjectNumberAlreadyExistedException projectNumberAlreadyExistedException){
        var errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.toString(),
                projectNumberAlreadyExistedException.getMessage(),
                ErrorResponseKey.ERROR_RESPONSE_PROJECT_NUMBER_EXISTED,
                projectNumberAlreadyExistedException.getProjectNumber().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ProjectNotExistedException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotExistedException(ProjectNotExistedException projectNotExistedException){
        var errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.toString(),
                projectNotExistedException.getMessage(),
                ErrorResponseKey.ERROR_RESPONSE_PROJECT_NUMBER_EXISTED,
                projectNotExistedException.getProjectId().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(SQLTransientConnectionException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseConnectionException(SQLTransientConnectionException sqlTransientConnectionException){
        var errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.toString(),
                sqlTransientConnectionException.getMessage(),
                ErrorResponseKey.ERROR_RESPONSE_DATABASE_CONNECTION,
                "");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
