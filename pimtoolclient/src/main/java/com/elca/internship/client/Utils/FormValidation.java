package com.elca.internship.client.Utils;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;

public class FormValidation {
    private Map<String, Boolean> formFields;

    public FormValidation(){formFields = new HashMap<>();}

    public Map<String, Boolean> getFormFields(){
        return formFields;
    }

    public static int iconSize;

    public static ValidatedResponse isName(String name, Label responseLabel){
        var valid = false;
        if(name == null){
            valid = false;
        }
        else {
            var exp = "^[A-Za-z\\s]+$";
            valid = name.matches(exp);
        }
        var msg = "Only alphabets and spaces allowed";
        return validationResponse(responseLabel,valid,msg);
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
