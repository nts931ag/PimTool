package com.elca.internship.server.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageProjectDto {
    private long totalElements;
    private List<ProjectDto> projectDtoList;
}
