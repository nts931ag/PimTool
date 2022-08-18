package com.elca.internship.server.exceptions;

import lombok.Data;

@Data
public class ProjectNotExistedException extends RuntimeException {
    private Long projectId;
    public ProjectNotExistedException(Long projectId) {
        super(String.format("Project %s is not existed!", projectId));
        this.projectId = projectId;
    }
}
