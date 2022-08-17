package com.elca.internship.client.webclient;

import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.exception.ProjectException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(value= SpringRunner.class)
@SpringBootTest
public class ProjectRestClientTest {
//    @Autowired
    /*private ProjectRestConsume projectRestConsume;

    @Test
    public void testCreateNewProject(){
        var date = LocalDate.now();
        var project = new Project(0,4444,"thaison","thaison", Project.Status.NEW, date, date,1);
        var listMember = new ArrayList<String>();

        Assertions.assertThrows(ProjectException.class, () -> projectRestConsume.createNewProjectTest(project, listMember));
    }*/
}
