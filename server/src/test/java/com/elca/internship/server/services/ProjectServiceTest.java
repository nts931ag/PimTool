package com.elca.internship.server.services;

import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.Employee;
import com.elca.internship.server.repositories.EmployeeRepository;
import com.elca.internship.server.repositories.ProjectRepository;
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
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;

    public ProjectServiceTest(){

    }

    public void createEmployee(int size){
        var listEmployee = new ArrayList<Employee>();
        for(int i = 0; i < size; ++i){
            listEmployee.add(
                    new Employee(
                            null,
                            null,
                            "EM" + i,
                            "EM" + i,
                            "EM" + i,
                            LocalDate.now(),
                            null,
                            null
                    )
            );
        }
        employeeRepository.saveAll(listEmployee);
    }

    public void createProjectDto(int size){
        var listProject = new ArrayList<ProjectDto>();
        for(int i=0;i<size;++i){
            listProject.add(
                    new ProjectDto(
                            null,
                            null,
                            0L,
                            i,
                            "project"+i,
                            "project"+i,
                            Status.NEW,
                            LocalDate.now(),
                            LocalDate.now()
                    )
            );
        }
    }

    @Test
    public void createProject(){
        createEmployee(5);
        var listVisa = List.of(
                "EM1",
                "EM2",
                "EM3"
        );
        var newProjectDto = new ProjectDto(
                null,
                null,
                0L,
                1,
                "project",
                "project",
                Status.NEW,
                LocalDate.now(),
                LocalDate.now()
        );
        projectService.createNewProject(newProjectDto, listVisa);
    }
    @Test
    public void updateProject(){
        var newProjectDto = new ProjectDto(
                1L,
                null,
                1L,
                1,
                "project modify",
                "project modify",
                Status.NEW,
                LocalDate.now(),
                LocalDate.now()
        );
        var listVisa = List.of(
                "EM4"
        );
        projectService.updateProject(newProjectDto, listVisa);
    }

    @Test
    public void getProjectByCriteriaAndStatus(){

    }

    @Test
    public void deleteProjectById(){
        projectService.deleteProject(1L);
    }

    @Test
    public void getAllProjectWithPagination(){
    }

}
