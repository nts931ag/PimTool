package com.elca.internship.server.models.dto;

import com.elca.internship.server.models.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record ProjectForm(
        ProjectDto project,
        List<String> listMember
) {
}
