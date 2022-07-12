package com.elca.internship.client.Utils;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.lang.management.MemoryManagerMXBean;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FormValidation {
    private Map<String, Boolean> formFields;

    public FormValidation(){formFields = new HashMap<>();}

    public Map<String, Boolean> getFormFields(){
        return formFields;
    }

    public static int iconSize;

    public static ValidatedResponse isProNumExisted(String val, Label responseLabel ) {
        boolean valid;
        if(val == null){
            valid = false;
        } else{
            String exp = "[\\d+]{0,19}";
            valid = val.matches(exp);
        }
        var msg = "Please enter a valid number project";
        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isProjectName(String val, Label responseLabel) {
        boolean valid;
        if(val == null){
            valid = false;
        } else{
            String exp = "^[A-Za-z][A-Za-z0-9_]{0,50}$";
            valid = val.matches(exp);

        }
        var msg = "Only alphabets, numbers and underscore is allowed. Char Limit: Min-1, Max-50";
        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isProjectCustomer(String val, Label responseLabel) {
        boolean valid;
        if(val == null){
            valid = false;
        } else{
            String exp = "^[A-Za-z][A-Za-z0-9_]{0,50}$";
            valid = val.matches(exp);

        }
        var msg = "Only alphabets, numbers and underscore is allowed. Char Limit: Min-1, Max-50";
        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isValidatedEndDate(LocalDate endVal, LocalDate startVal, Label responseLabel) {
        boolean valid;
        if(startVal == null || endVal == null){
            valid = false;
        } else{
            valid = startVal.isBefore(endVal);
        }
        var msg = "End date must be less than start date";
        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse validationResponse(Label responseLabel, boolean valid,String msg){
        FormValidation.iconSize = 24;
//        IconFontFX.register(GoogleMaterialDesignIcons.getIconFont());

        responseLabel.setText("");
        responseLabel.setGraphic(null);
        responseLabel.getStyleClass().clear();
        responseLabel.setWrapText(true);

        if(valid){
            responseLabel.setText("");
//            IconNode correctIcon = new IconNode(GoogleMaterialDesignIcons.CHECK_CIRCLE);
//            correctIcon.setIconSize(iconSize);
//            correctIcon.setFill(Color.web("#152769"));
//            responseLabel.setGraphic(correctIcon);
            responseLabel.getStyleClass().add("validate-success");
            responseLabel.setTooltip(new Tooltip(msg));
            responseLabel.setWrapText(true);
            return new ValidatedResponse(responseLabel,true);
        }
        else{
            responseLabel.setFont(new Font(responseLabel.getPrefHeight() * 0.50));
            responseLabel.setText(msg);
//            IconNode wrongIcon = new IconNode(GoogleMaterialDesignIcons.HIGHLIGHT_OFF);
//            wrongIcon.setIconSize(iconSize);
//            wrongIcon.setFill(Color.web("#8c2c20"));
//            responseLabel.setGraphic(wrongIcon);
            responseLabel.getStyleClass().add("validate-err");
            responseLabel.setTooltip(new Tooltip(msg));
            responseLabel.setWrapText(true);
            return new ValidatedResponse(responseLabel,false);
        }
    }



}
