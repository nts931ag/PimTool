package com.elca.internship.server.exceptions.handler;


public record ErrorResponse(String statusError,
    String statusMsg,
    ErrorResponseKey i18nKey,
    String i18nValue
){}

