package com.elca.internship.server.services;

import com.elca.internship.server.dao.ProjectDAO;
import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.exceptions.EmployeeNotExistedException;
import com.elca.internship.server.exceptions.GroupNotExistedException;
import com.elca.internship.server.repositories.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.List;


@RunWith(value= SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;
//    @Autowired
//    public ProjectServiceTest(ProjectService projectService){
//        this.projectService = projectService;
//    }

    @Test
    public void createNewProject(){
        var project = new ProjectDto(0L,9301,"thaison","thaison", Status.NEW, LocalDate.now(),LocalDate.now());
        projectService.createProject(project, List.of("NGU"));
    }

    @Test
    public void updateProjectExisting(){
        /*var project = new Project(1L,3,"mobile","Bosch", Project.Status.INP, LocalDate.now(),LocalDate.now(),1);
        var newProject = projectService.updateProject(1L,project);

        Assert.assertEquals(project.getCustomer(), newProject.getCustomer());*/

        var projectDto = new ProjectDto();
        var project = new ProjectDto(42L,1, 3936L,8886,"mobile","Bosch", Status.NEW, LocalDate.now(),LocalDate.now());
        projectService.updateProject(project, List.of("NGU", "TRA", "DUY"));
    }

    @Test
    public void getAllCurrentProject(){
       /* var listProject = projectService.getAllProject();
        int count = 0;

        for (Project project:listProject) {
            System.out.println(project);
            count++;
        }
        Assert.assertEquals(2, count);*/
    }

    @Test
    public void updateNewProject() throws GroupNotExistedException, EmployeeNotExistedException {
        /*var project = new Project(
                1L,
                1,
                1,
                "mobile",
                "HCMUS",
                Status.NEW,
                LocalDate.now(),
                LocalDate.now(),
                2
        );*/
        var listEmployee = List.of("TTT", "NGU", "TRA");
    }

    @Test
    public void findAllProjectSpecifiedWithPagination(){
        var listProject = projectService.getProjectByCriteriaWithPagination("","NEW",0,4);
        listProject.stream().forEach(System.out::println);
    }
}
