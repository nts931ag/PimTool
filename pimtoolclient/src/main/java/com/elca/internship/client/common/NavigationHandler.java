package com.elca.internship.client.common;

import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.models.Project;

public interface NavigationHandler {
    void handleNavigateToCreateProject();
    void handleNavigateToEditProject(Project project);
    void handleNavigateToListProject(boolean updateMode);
    void handleNavigateToErrorPage(I18nKey msg);

}
