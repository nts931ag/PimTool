package com.elca.internship.client.test;

import com.elca.internship.client.i18n.I18nKey;
import lombok.Data;

@Data
public class ProjectException extends RuntimeException{
    private ErrorResponseKey i18nKey;
    private String i18nValue;
    public ProjectException(String msg, ErrorResponseKey i18nKey, String i18nValue){
        super(msg);
        this.i18nKey = i18nKey;
        this.i18nValue = i18nValue;
    }
}
