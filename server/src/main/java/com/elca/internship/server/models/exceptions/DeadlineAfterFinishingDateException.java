package com.elca.internship.server.models.exceptions;

import java.time.LocalDate;

public class DeadlineAfterFinishingDateException extends Exception {
    private final LocalDate projectFinishingDate;
    private final LocalDate taskDeadline;

    public DeadlineAfterFinishingDateException(LocalDate projectFinishingDate, LocalDate taskDeadline) {
        super(String.format("Deadline %s is after finishing date %s.", taskDeadline, projectFinishingDate));
        this.projectFinishingDate = projectFinishingDate;
        this.taskDeadline = taskDeadline;
    }

    public LocalDate getProjectFinishingDate() {
        return projectFinishingDate;
    }

    public LocalDate getTaskDeadline() {
        return taskDeadline;
    }
}
