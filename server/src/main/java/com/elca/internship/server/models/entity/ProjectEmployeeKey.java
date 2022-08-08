package com.elca.internship.server.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEmployeeKey implements Serializable {

    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "employee_id")
    private Long employeeId;
}
