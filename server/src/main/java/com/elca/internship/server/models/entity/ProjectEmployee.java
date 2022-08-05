package com.elca.internship.server.models.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Table(name = "project_employee", schema = "pim_tool_db_migration")
@Entity
public class ProjectEmployee {
//    private long projectId;
//    private long employeeId;
    @EmbeddedId
    ProjectEmployeeKey id;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
