package com.elca.internship.server.models.record;

import com.elca.internship.server.models.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ProjectRecord(Long id, Integer version, Long groupId, Integer projectNumber, String name, String customer,
                            Status status, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate startDate, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
}
