package com.elca.internship.server.controllers;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;


    @PostMapping("/save")
    public Long createNewProject(){
        var project = new Project(1L,3,"mobile","Bosch", Project.Status.INP, LocalDate.now(),LocalDate.now(),1);
        return projectService.createNewProject(project);
    }

}
