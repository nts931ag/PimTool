package com.elca.internship.server.DAOTest;

import com.elca.internship.server.dao.EmployeeDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(value= SpringRunner.class)
@SpringBootTest
public class EmployeeDAOTest {
    @Autowired
    private EmployeeDAO employeeDAO;
//    @Autowired
//    public ProjectServiceTest(ProjectService projectService){
//        this.projectService = projectService;
//    }

    @Test
    public void getIdVisaByListVisa(){
        /*var project = new Project(1L,3,"mobile","Bosch", Project.Status.INP, LocalDate.now(),LocalDate.now(),1);
        var id = projectService.createNewProject(project);
        project.setId(id);
        var resProject = projectService.getProject(3L);
        Assert.assertEquals(project, resProject);*/
        var listVisa = List.of("NGU", "TRA", "NTT");
        var result = employeeDAO.getMapVisaIdByListVisa(listVisa);
        System.out.println(result);
    }

}
