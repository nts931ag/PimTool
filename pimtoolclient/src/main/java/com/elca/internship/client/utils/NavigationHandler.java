package com.elca.internship.client.utils;

import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.models.entity.Project;

public interface NavigationHandler {
    void handleNavigateToCreateProject();
    void handleNavigateToEditProject(Project project);
    void handleNavigateToListProject();
    void handleNavigateToErrorPage(I18nKey msg);

}
