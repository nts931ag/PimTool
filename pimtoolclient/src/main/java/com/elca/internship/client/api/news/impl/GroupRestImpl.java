package com.elca.internship.client.api.news.impl;


import com.elca.internship.client.api.news.GroupRest;
import com.elca.internship.client.models.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class GroupRestImpl implements GroupRest {

    public static final String URI_GET_ALL_GROUP = "http://localhost:8080/api/groups";
    public static final String URI_GET_ALL_ID_GROUP = "http://localhost:8080/api/groups/ids";
    private final WebClient webClient;

    @Override
    public Flux<Group> getAllGroup() {
        return webClient.get().uri(URI_GET_ALL_GROUP).retrieve().bodyToFlux(Group.class);
    }

    @Override
    public Flux<Integer> getAllIdGroup() {
        return webClient.get().uri(URI_GET_ALL_ID_GROUP).retrieve().bodyToFlux(Integer.class);
    }
}
