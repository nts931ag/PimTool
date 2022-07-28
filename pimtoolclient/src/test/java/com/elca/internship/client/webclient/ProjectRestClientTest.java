package com.elca.internship.client.webclient;

import com.elca.internship.client.api.ClientDataException;
import com.elca.internship.client.consume.ProjectRestConsume;
import com.elca.internship.client.models.entity.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@RunWith(value= SpringRunner.class)
@SpringBootTest
public class ProjectRestClientTest {
    @Autowired
    private ProjectRestConsume projectRestConsume;

    @Test
    public void testCreateNewProject(){
        var date = LocalDate.now();
        var project = new Project(0,1234,"thaison","thaison", Project.Status.NEW, date, date,1);
        var listMember = List.of("NVA", "NGU");

        Assertions.assertThrows(ClientDataException.class, () -> projectRestConsume.createNewProjectTest(project, listMember));
    }
}
