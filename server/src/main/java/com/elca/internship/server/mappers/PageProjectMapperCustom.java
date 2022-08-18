package com.elca.internship.server.mappers;

import com.elca.internship.server.models.dto.PageProjectDto;
import com.elca.internship.server.models.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageProjectMapperCustom {

    private final ProjectMapperCustom projectMapperCustom;

    public PageProjectDto pageProjectToPageProjectDto(Page<Project> projectPage){
        if(projectPage == null){
            return null;
        }

        var pageProjectDto = new PageProjectDto();
        pageProjectDto.setTotalElements(projectPage.getTotalElements());
        pageProjectDto.setProjectDtoList(
                projectMapperCustom.listEntityToListDto(projectPage.getContent())
        );



        return pageProjectDto;
    }
}
