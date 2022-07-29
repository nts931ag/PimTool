package com.elca.internship.client.test;

public enum ErrorResponseKey {

    ERROR_RESPONSE_PROJECT_NUMBER_EXISTED("error.response.project.number.existed"),
    ERROR_RESPONSE_PROJECT_EMPLOYEE_NOT_EXISTED("error.response.project.employee.not.existed"),
    ERROR_RESPONSE_PROJECT_GROUP_NOT_EXISTED("error.response.project.group.not.existed"),
    ERROR_RESPONSE_PROJECT_MEMBER_NOT_EMPTY("error.response.project.member.not.empty"),
    ERROR_RESPONSE_DATABASE_CONNECTION("error.response.database.connection");

    private final String key;

    ErrorResponseKey(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
