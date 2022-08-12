package com.elca.internship.server.validator;

import com.elca.internship.server.exceptions.ProjectNumberAlreadyExistedException;
import com.elca.internship.server.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProjectValidator {
    private final ProjectRepository projectRepository;

    public void validateProjectNumberAlreadyExisted(Integer projectNumber){
        var project = projectRepository.findByProjectNumber(projectNumber);
        project.ifPresent(p -> {throw new ProjectNumberAlreadyExistedException(projectNumber);});
    }

}
