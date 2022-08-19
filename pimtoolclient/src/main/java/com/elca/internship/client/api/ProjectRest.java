package com.elca.internship.client.api;

import com.elca.internship.client.models.Project;
import com.elca.internship.client.models.ProjectFormRecord;
import com.elca.internship.client.models.ProjectPageRecord;
import javafx.collections.ObservableSet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProjectRest {
    Flux<Project> getAllProject();

    void deleteProjectById(Long id);

    void deleteProjectByIds(List<Long> listIdDelete);

    Mono<Project> getProjectByProjectNumber(Integer projectNumber);

    void createNewProject(ProjectFormRecord projectFormRecord);

    void updateProject(ProjectFormRecord projectFormRecord);

    ObservableSet<Project> getProjectWithPagination(String tfSearchValue, String cbStatusValue, int limit, int offset);

    ProjectPageRecord searchProjectByCriteriaAndStatusWithPagination(String tfSearchValue, String status, long limit, long offset);
}
