package com.elca.internship.server.DAOTest;

import com.elca.internship.server.dao.ProjectDAO;
import com.elca.internship.server.models.entity.Project;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(value= SpringRunner.class)
@SpringBootTest
public class ProjectDAOTest {
    @Autowired
    private ProjectDAO projectDAO;
    @Test
    public void getAllProjectWithPagination(){
        PageRequest pageRequest = PageRequest.of(0,6, Sort.Direction.fromString("ASC"), "PROJECT_NUMBER");
        Page<Project> projectPage = projectDAO.findAllProjectWithPagination(pageRequest);
        projectPage.get().toList().stream().forEach(System.out::println);
    }


}
