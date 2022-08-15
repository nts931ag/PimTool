package com.elca.internship.server.models.dto;

import com.elca.internship.server.models.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto extends BaseDto {
    private Long groupId;
    private Integer projectNumber;
    private String name;
    private String customer;
    private Status status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    public ProjectDto(Long id, Integer version, Long groupId, Integer projectNumber, String name, String customer, Status status, LocalDate startDate, LocalDate endDate) {
        super(id, version);
        this.groupId = groupId;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
