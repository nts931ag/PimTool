package com.elca.internship.server.models.dto;

public record SearchProjectForm(String proCriteria, String proStatus, Integer limit, Integer offset){}
