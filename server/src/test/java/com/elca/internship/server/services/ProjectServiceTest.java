package com.elca.internship.server.services;

import com.elca.internship.server.models.entity.Project;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;


@RunWith(value= SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;
//    @Autowired
//    public ProjectServiceTest(ProjectService projectService){
//        this.projectService = projectService;
//    }

    @Test
    public void createNewProject(){
        var project = new Project(1L,3,"mobile","Bosch", Project.Status.INP, LocalDate.now(),LocalDate.now(),1);
        var id = projectService.createNewProject(project);
        project.setId(id);
        var resProject = projectService.getProject(3L);
        Assert.assertEquals(project, resProject);
    }

    @Test
    public void updateProjectExisting(){
        var project = new Project(1L,3,"mobile","Bosch", Project.Status.INP, LocalDate.now(),LocalDate.now(),1);
        var newProject = projectService.updateProject(1L,project);

        Assert.assertEquals(project.getCustomer(), newProject.getCustomer());
    }

    @Test
    public void getAllCurrentProject(){
        var listProject = projectService.getAllProject();
        int count = 0;

        for (Project project:listProject) {
            System.out.println(project);
            count++;
        }
        Assert.assertEquals(2, count);
    }
}
