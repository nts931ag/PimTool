package com.elca.internship.server.mappers;

import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.Project;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapperCustom {

    public ProjectDto entityToDto(Project entity){
        return new ProjectDto(
                entity.getId(),
                entity.getVersion(),
                entity.getGroup().getId(),
                entity.getProjectNumber(),
                entity.getName(),
                entity.getCustomer(),
                entity.getStatus(),
                entity.getStartDate(),
                entity.getEndDate()
        );
    }

    public List<ProjectDto> listEntityToListDto(List<Project> listEntity){
        var listDto = new ArrayList<ProjectDto>();
        listEntity.stream().forEach(project -> {
            listDto.add(
                    this.entityToDto(project)
            );
        });
        return listDto;
    }
}
