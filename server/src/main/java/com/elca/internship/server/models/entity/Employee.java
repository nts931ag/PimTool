package com.elca.internship.server.models.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Table(name = "employee", schema = "pim_tool_db_migration")
@Entity
@Getter
@Setter
public class Employee extends BaseEntity{
    @Column(name = "visa")
    private String visa;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @OneToOne(mappedBy = "groupLeaderId")
    private Group group;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<ProjectEmployee> projectEmployee;

    /*public Employee(long id, String visa, String firstName, String lastName, LocalDate birthDate, int version){
        *//*super(id, version);
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;*//*
    }*/

}
