package com.elca.internship.client.models;

import lombok.Data;

import java.util.List;

@Data
public class PageProject {
    private long totalElements;
    private List<Project> projectList;
}
