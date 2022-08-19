package com.elca.internship.client.api;

import com.elca.internship.client.models.Group;
import reactor.core.publisher.Flux;

public interface GroupRest {
    Flux<Group> getAllGroup();

    Flux<Integer> getAllIdGroup();
}
