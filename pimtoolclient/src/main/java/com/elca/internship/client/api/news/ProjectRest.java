package com.elca.internship.client.api.news;

import com.elca.internship.client.models.entity.Project;
import javafx.collections.ObservableSet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProjectRest {
    Flux<Project> getAllProject();

    void deleteProjectById(Long id);

    void deleteProjectByIds(List<Long> listIdDelete);

    Mono<Project> getProjectByProjectNumber(Integer projectNumber);

    void createNewProject(String jsonObject);

    void updateProject(String jsonObject);

    ObservableSet<Project> getProjectWithPagination(String tfSearchValue, String cbStatusValue, int limit, int offset);
}
