package com.elca.internship.client.api.news;


import com.elca.internship.client.models.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ProjectApi {

    private final WebClient webClient;

    public Flux<Project> getAllProject() {
        return webClient.get().uri("http://localhost:8080/api/projects")
                .retrieve()
                .bodyToFlux(Project.class);
    }
}
