package com.elca.internship.server.models.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee extends BaseEntity{
    private String visa;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public Employee(long id, String visa, String firstName, String lastName, LocalDate birthDate, int version){
        super(id, version);
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
