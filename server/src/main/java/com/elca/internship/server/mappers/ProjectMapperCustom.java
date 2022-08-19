package com.elca.internship.server.mappers;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.record.ProjectRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProjectMapperCustom {

    public ProjectRecord entityToRecord(Project entity){
        if(entity == null){
            return null;
        }
        return new ProjectRecord(
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

    public List<ProjectRecord> listEntityToListRecord(List<Project> listEntity){
        if(listEntity == null){
            return Collections.emptyList();
        }
        var listDto = new ArrayList<ProjectRecord>();
        listEntity.forEach(project -> listDto.add(
                this.entityToRecord(project)
        ));
        return listDto;
    }
}
