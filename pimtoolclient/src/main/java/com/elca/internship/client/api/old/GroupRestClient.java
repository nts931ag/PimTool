package com.elca.internship.client.api.old;

import com.elca.internship.client.controllers.DashboardController;
import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.models.entity.Group;
import com.elca.internship.client.models.entity.Project;
import javafx.application.Platform;
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
                .doOnError(
                throwable -> Platform.runLater(
                        () -> DashboardController.navigationHandler
                                .handleNavigateToErrorPage(I18nKey.APPLICATION_ERROR_CONNECTION)
                )
        )
                .onErrorReturn(List.of())
                .block();
    }
}
