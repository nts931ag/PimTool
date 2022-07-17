package com.elca.internship.client.i18n;

public enum I18nKey {

    /* Dashboard ui */
    DASHBOARD_TITLE("dashboard.title"),
    DASHBOARD_MENU("dashboard.menu"),
    DASHBOARD_MENU_LIST_PROJECT("dashboard.menu.list.project"),
    DASHBOARD_MENU_LIST_PROJECT_TITLE("dashboard.menu.list.project.title"),
    DASHBOARD_MENU_CREATE_PROJECT("dashboard.menu.create.project"),
    DASHBOARD_MENU_CREATE_PROJECT_TITLE("dashboard.menu.create.project.title"),
    DASHBOARD_MENU_CUSTOMER("dashboard.menu.customer"),
    DASHBOARD_MENU_SUPPLIER("dashboard.menu.supplier"),
    DASHBOARD_HEADER_LANGUAGE_EN("dashboard.menu.language.en"),
    DASHBOARD_HEADER_LANGUAGE_FR("dashboard.menu.language.fr"),
    DASHBOARD_HEADER_HELP("dashboard.menu.help"),
    DASHBOARD_HEADER_LOGOUT("dashboard.menu.logout"),

    /* New Project ui */

    LABEL_PROJECT_NUMBER("label.project.number"),
    LABEL_PROJECT_NAME("label.project.name"),
    LABEL_PROJECT_CUSTOMER("label.project.customer"),
    LABEL_PROJECT_GROUP("label.project.group"),
    LABEL_PROJECT_MEMBERS("label.project.members"),
    LABEL_PROJECT_STATUS("label.project.status"),
    LABEL_PROJECT_START_DATE("label.project.start.date"),
    LABEL_PROJECT_END_DATE("label.project.end.date"),
    BUTTON_CREATE_PROJECT("button.create.project"),
    BUTTON_CANCEL_CREATE_PROJECT("button.cancel.create.project"),

    /* Project list ui */
    BUTTON_SEARCH_PROJECT("button.search.project"),
    BUTTON_RESET_SEARCH_PROJECT("button.reset.search.project"),
    TEXTFIELD_PROMPT_TEXT_SEARCH("textfield.prompt.text.search"),
    COMBOBOX_PROMPT_TEXT_STATUS("combobox.prompt.text.status"),
    PROJECT_TABLE_COL_NUMBER("project.table.col.number"),
    PROJECT_TABLE_COL_NAME("project.table.col.name"),
    PROJECT_TABLE_COL_STATUS("project.table.col.status"),
    PROJECT_TABLE_COL_CUSTOMER("project.table.col.customer"),
    PROJECT_TABLE_COL_START_DATE("project.table.col.start.date"),
    PROJECT_TABLE_COL_DELTE("project.table.col.delete"),

    /* Combobox status ui */
    COMBOBOX_PROJECT_STATUS("combobox.project.status"),
    COMBOBOX_NEW_PROJECT_STATUS("combobox.new.project.status"),
    COMBOBOX_FINISHED_PROJECT_STATUS("combobox.finished.project.status"),
    COMBOBOX_PLANNED_PROJECT_STATUS("combobox.planned.project.status"),
    COMBOBOX_IN_PROGRESS_PROJECT_STATUS("combobox.in.progress.project.status"),
    ;

    private final String key;

    I18nKey(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
