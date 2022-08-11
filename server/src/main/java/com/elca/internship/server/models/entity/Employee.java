package com.elca.internship.server.models.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<ProjectEmployee> projectEmployee = new HashSet<>();

    public void addChildProjectEmployee(ProjectEmployee childProjectEmployee){
        if(projectEmployee == null){
            this.projectEmployee = new LinkedHashSet<ProjectEmployee>();
        }
        this.projectEmployee.add(childProjectEmployee);
    }

    public void removeChildProjectEmployee(ProjectEmployee childProjectEmployee){
        this.projectEmployee.remove(childProjectEmployee);

    }

    /*public Employee(long id, String visa, String firstName, String lastName, LocalDate birthDate, int version){
        *//*super(id, version);
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;*//*
    }*/

}
