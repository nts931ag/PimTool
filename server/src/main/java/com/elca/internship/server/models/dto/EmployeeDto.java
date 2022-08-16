package com.elca.internship.server.models.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDto extends BaseDto {
    private String visa;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    public EmployeeDto(Long id, Integer version, String visa, String firstName, String lastName, LocalDate date) {
        super(id, version);
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
    }
}
