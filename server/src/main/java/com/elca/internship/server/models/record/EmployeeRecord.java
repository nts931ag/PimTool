package com.elca.internship.server.models.record;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record EmployeeRecord(Long id, Integer version,String visa, String firstName, String lastName, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate birthDate) {
}
