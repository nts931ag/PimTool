package com.elca.internship.client.models.entity;


import lombok.Data;

import java.time.LocalDate;

@Data
public class Employee extends BaseEntity {
    private String visa;
    private String firstName;
    private String lastName;
    private LocalDate date;
}
