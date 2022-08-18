package com.elca.internship.server.adapter;

import com.elca.internship.server.mappers.PageProjectMapperCustom;
import com.elca.internship.server.mappers.ProjectMapperCustom;
import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.dto.PageProjectDto;
import com.elca.internship.server.models.dto.ProjectDto;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.services.news.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectAdapter {
    private final ProjectService projectService;
    private final ObjectMapper objectMapper;
    private final ProjectMapperCustom projectMapperCustom;
    private final PageProjectMapperCustom pageProjectMapperCustom;

    public void updateAndModifyMemberOfProjectSpecified(String jsonObject) {
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), ProjectDto.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            log.info("Info project update: " + project);
            log.info("Info list member update: " + listEmployeeVisa);
            projectService.updateProject(project, listEmployeeVisa);
        } catch (JsonProcessingException jpe) {
            System.out.println(jpe.getMessage());
        }
    }

    public void createAndAddMembersIntoNewProject(String jsonObject){
        try {
            var jsonNode = objectMapper.readTree(jsonObject);
            var project = objectMapper.treeToValue(jsonNode.get("project"), ProjectDto.class);
            var listEmployeeVisa = objectMapper.treeToValue(jsonNode.get("listMember"), List.class);
            log.info("Info new project: " + project);
            log.info("Info list member: " + listEmployeeVisa);
            projectService.createNewProject(project, listEmployeeVisa);
        } catch (JsonProcessingException jpe) {
            System.out.println(jpe.getMessage());
        }
    }

    public PageProjectDto getProjectByCriteriaAndStatusSpecified(String proCriteria, String proStatus, Integer limit, Integer offset) {
        Pageable page = PageRequest.of(offset, limit, Sort.by("projectNumber").ascending());
        Status status = null;
        var statusList = Arrays.stream(Status.values()).collect(Collectors.toList())
                .stream().map(Object::toString).collect(Collectors.toList());

        if(statusList.contains(proStatus)){
            status = Status.valueOf(proStatus);
        }

        var pageProjectDto = projectService.getAllProjectByCriteriaAndStatusWithPagination(proCriteria, status, page);
        return pageProjectMapperCustom.pageProjectToPageProjectDto(pageProjectDto);
    }

    public ProjectDto getProjectByProjectNumber(Integer projectNumber) {
        var project = projectService.getProjectByProjectNumber(projectNumber);
        return projectMapperCustom.entityToDto(project);
    }
}
