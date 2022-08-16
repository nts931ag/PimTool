package com.elca.internship.client.api.news;

import com.elca.internship.client.models.entity.Group;
import reactor.core.publisher.Flux;

public interface GroupRest {
    Flux<Group> getAllGroup();

    Flux<Integer> getAllIdGroup();
}
