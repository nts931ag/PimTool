package com.elca.internship.server.models.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDto extends BaseDto {
    private String visa;
    private String firstName;
    private String lastName;
    private LocalDate date;
}
