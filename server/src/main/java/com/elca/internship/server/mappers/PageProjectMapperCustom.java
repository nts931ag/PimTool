package com.elca.internship.server.mappers;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.record.ProjectPageRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageProjectMapperCustom {

    private final ProjectMapperCustom projectMapperCustom;

    public ProjectPageRecord projectPageToProjectPageRecord(Page<Project> projectPage){
        if(projectPage == null){
            return null;
        }

        return new ProjectPageRecord(
                projectPage.getTotalElements(),
                projectMapperCustom.listEntityToListRecord(projectPage.getContent())
        );
    }
}
