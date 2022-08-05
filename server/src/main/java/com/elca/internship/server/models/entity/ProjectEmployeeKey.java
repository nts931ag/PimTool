package com.elca.internship.server.models.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProjectEmployeeKey implements Serializable {

    @Column(name = "project_id")
    private long projectId;
    @Column(name = "employee_id")
    private long employeeId;
}
