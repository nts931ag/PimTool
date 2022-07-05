package com.elca.internship.server.models.entity;


import lombok.Data;

import java.time.LocalDate;

@Data
public class Employee {
    private long id;
    private String visa;
    private String firstName;
    private String lastName;
    private LocalDate date;
    private int version;
}
