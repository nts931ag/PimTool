package com.elca.internship.server.models.entity;


import lombok.*;

import javax.persistence.*;

@Table(name = "project_employee", schema = "pim_tool_db_migration")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEmployee {
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
