package com.elca.internship.server.models.dto;

import com.elca.internship.server.models.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProjectDto extends BaseDto {
    private Long groupId;
    private Integer projectNumber;
    private String name;
    private String customer;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;

}
