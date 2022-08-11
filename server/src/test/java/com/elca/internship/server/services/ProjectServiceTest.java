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
}
