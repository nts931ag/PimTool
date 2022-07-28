package com.elca.internship.client.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String statusError;
    private String statusMsg;
    private ErrorResponseKey i18nKey;
    private String i18nValue;
}
