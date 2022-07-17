package com.elca.internship.client.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.javafx.IconFontFX;
import jiconfont.javafx.IconNode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormValidation {
    private Map<String, Boolean> formFields;

    public FormValidation(){formFields = new HashMap<>();}



    public Map<String, Boolean> getFormFields(){
        return formFields;
    }

    public static int iconSize;

    public static ValidatedResponse isProNumValid(String val, ObservableList<Integer> listCurProNum, Label responseLabel ) {
        boolean valid;
        var msg = "";
        if(val == null){
            valid = false;
        } else if (val.isBlank()){
            valid = false;
            msg = "This field can't be blank.";
        } else{
            String exp = "[\\d+]{0,19}";
            valid = val.matches(exp);
            if(!valid){
                msg = "Only numbers is allowed. Length limit: Min-1, Max-19.";
            }else{
                valid = !listCurProNum.contains(Long.parseLong(val));
                msg = "The project number already existed. Please select a different project number.";
            }
        }
        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isProNameValid(String val, Label responseLabel) {
        boolean valid;
        var msg = "";
        if(val == null){
            valid = false;
        } else if (val.isBlank()) {
            valid = false;
            msg = "This field can't be blank.";
        }
        else{
            String exp = "^[A-Za-z][A-Za-z\\d_\s+]{0,50}$";
            valid = val.matches(exp);
            msg = "Begin with alphabets. Numbers, underscore and whitespace is allowed. Length Limit: Min-1, Max-50.";
        }
        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isProCustomerValid(String val, Label responseLabel) {
        boolean valid;
        var msg = "";
        if(val == null){
            valid = false;
        } else if (val.isBlank()) {
            valid = false;
            msg = "This field can't be blank.";
        } else{
            String exp = "^[A-Za-z][A-Za-z\\d_\s+]{0,50}$";
            valid = val.matches(exp);
            msg = "Begin with alphabets. Numbers, underscore and whitespace is allowed. Length Limit: Min-1, Max-50.";
        }
        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isDateValid(LocalDate endVal, LocalDate startVal, Label responseLabel) {
        boolean valid;
        var msg = "";
        if(startVal == null || endVal == null){
            valid = false;
            msg = "End date or Start date must not be empty.";
        } else{
            if(endVal.isBefore(startVal)){
                msg = "End date must be less than start date.";
                valid = false;
            }else{
                valid = true;
            }
        }
        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isProMemberValid(String newValue, ObservableList<String> listCurProMember, Label responseLabel) {
        boolean valid = true;
        var listInvalidVisa = new ArrayList<String>();

        if(newValue == null){
            valid = false;
        } else{
//            var regex = "[A-Z+]{3}$";
            var listVisa = newValue.split(", ");
            for(int i=0;i<listVisa.length;++i){
                if(!listCurProMember.contains(listVisa[i])){
                    listInvalidVisa.add(listVisa[i]);
                    valid = false;
                }
            }
            if(listInvalidVisa.isEmpty()){
                valid = true;
            }
        }
        var msg = "The following visas do not exist: " + listInvalidVisa.toString();
        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse validationResponse(Label responseLabel, boolean valid,String msg){
        FormValidation.iconSize = 24;
        IconFontFX.register(GoogleMaterialDesignIcons.getIconFont());

        responseLabel.setText("");
        responseLabel.setGraphic(null);
        responseLabel.getStyleClass().clear();
        responseLabel.setWrapText(true);

        if(valid){
            responseLabel.setText("");

            IconNode correctIcon = new IconNode(GoogleMaterialDesignIcons.CHECK_CIRCLE);
            correctIcon.setIconSize(iconSize);
            correctIcon.setFill(Color.web("#152769"));

            responseLabel.setGraphic(correctIcon);
            responseLabel.getStyleClass().add("validate-success");
            responseLabel.setTooltip(new Tooltip(msg));
            return new ValidatedResponse(responseLabel,true);
        }
        else{
            responseLabel.setFont(new Font(responseLabel.getPrefHeight() * 0.50));

            responseLabel.setText(msg);
            IconNode wrongIcon = new IconNode(GoogleMaterialDesignIcons.HIGHLIGHT_OFF);
            wrongIcon.setIconSize(iconSize);
            wrongIcon.setFill(Color.web("#8c2c20"));
            responseLabel.setGraphic(wrongIcon);

            responseLabel.getStyleClass().add("validate-err");
            responseLabel.setTooltip(new Tooltip(msg));
            return new ValidatedResponse(responseLabel,false);
        }
    }



}
