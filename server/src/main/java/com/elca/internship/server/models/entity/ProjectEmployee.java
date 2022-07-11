package com.elca.internship.server.models.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectEmployee {
    private long projectId;
    private long employeeId;
}
