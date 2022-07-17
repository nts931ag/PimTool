package com.elca.internship.client.api;

import com.elca.internship.client.models.entity.Group;
import com.elca.internship.client.models.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@RequiredArgsConstructor
public class GroupRestClient {
    public static final String URI_GET_ALL_GROUP = BASE_URI + "/api/groups";
    private final WebClient webClient;

    public List<Group> getAllProjects() {
        return webClient.get().uri(URI_GET_ALL_GROUP)
                .retrieve()
                .bodyToFlux(Group.class)
                .collectList()
                .block();
    }
}
