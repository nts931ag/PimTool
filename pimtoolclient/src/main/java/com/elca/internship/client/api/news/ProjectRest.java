package com.elca.internship.client.api.news;

import com.elca.internship.client.models.entity.Project;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ProjectRest {
    Flux<Project> getAllProject();

    void deleteProjectById(Long id);

    void deleteProjectByIds(List<Long> listIdDelete);
}
