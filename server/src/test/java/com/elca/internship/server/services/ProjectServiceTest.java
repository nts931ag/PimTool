package com.elca.internship.server.services;

import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.repositories.ProjectRepository;
import com.elca.internship.server.services.news.ProjectService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RunWith(value= SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;

    public ProjectServiceTest(){

    }

    @Test
    public void createNewProject(){
        var projectDto = new ProjectDto(
                null,
                null,
                0L,
                3,
                "mobile",
                "elca",
                Status.NEW,
                LocalDate.now(),
                LocalDate.now()
        );
        var listEmployee = new ArrayList<String>();
        listEmployee.add("EM1");
        listEmployee.add("EM2");
        projectService.createNewProject(projectDto, listEmployee);
    }

    @Test
    public void updateProject(){

        var projectDto = new ProjectDto(
                10L,
                null,
                9L,
                111,
                "modify1",
                "modify",
                Status.NEW,
                LocalDate.now(),
                LocalDate.now()
        );
        var listEmployee = new ArrayList<String>();
        listEmployee.add("EM4");
        listEmployee.add("EM5");
        projectService.updateProject(projectDto, listEmployee);
    }

    @Test
    public void deleteProject(){
        projectService.deleteProject(8L);

    }

    @Test
    public void deleteProjects(){
        projectService.deleteProjects(List.of(10L,11L,12L,13L));
    }
}
