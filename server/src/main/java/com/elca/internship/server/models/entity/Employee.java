package com.elca.internship.server.models.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
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
@NoArgsConstructor
public class Employee extends BaseEntity{
    @Column(name = "visa")
    private String visa;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "groupLeaderId", fetch = FetchType.LAZY)
    private Set<Group> group = new HashSet<>();
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private Set<ProjectEmployee> projectEmployee = new HashSet<>();

    public void addChildProjectEmployee(ProjectEmployee childProjectEmployee){
        if(projectEmployee == null){
            this.projectEmployee = new HashSet<>();
        }
        this.projectEmployee.add(childProjectEmployee);
    }

    public void removeChildProjectEmployee(ProjectEmployee childProjectEmployee){
        this.projectEmployee.remove(childProjectEmployee);

    }

    public Employee(Long id, Integer version, String visa, String firstName, String lastName, LocalDate birthDate, Set<Group> group, Set<ProjectEmployee> projectEmployee) {
        super(id, version);
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.group = group;
        this.projectEmployee = projectEmployee;
    }

    /*public Employee(long id, String visa, String firstName, String lastName, LocalDate birthDate, int version){
        *//*super(id, version);
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;*//*
    }*/

}
