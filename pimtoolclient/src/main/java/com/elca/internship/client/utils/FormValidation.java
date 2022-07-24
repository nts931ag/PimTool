package com.elca.internship.client.utils;

import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

/*    public static ValidatedResponse isProNumValid(String val, ObservableList<Integer> listCurProNum, Label responseLabel, I18nManager i18nManager) {
        boolean valid;
        var msg = "";
        if(val == null){
            valid = false;
        } else if (val.isBlank()){
            valid = false;
            msg = i18nManager.text(I18nKey.MSG_VALIDATED_BLANK);
        } else{
            String exp = "[\\d+]{0,19}";
            valid = val.matches(exp);
            if(!valid){
                msg = i18nManager.text(I18nKey.MSG_VALIDATED_FORMAT_NUMBER);
            }else{

                valid = !listCurProNum.contains(Long.parseLong(val));

                msg = i18nManager.text(I18nKey.MSG_VALIDATED_NUMBER_EXISTED);
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
        boolean valid = true;
        var msg = "";
        if(endVal != null && startVal != null){
            if(endVal.isBefore(startVal)){
                valid = false;
            }
        }else{
            if (startVal == null){
                valid = false;
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
    }*/

    public static ValidatedResponse isProMemberValidInput(String newValue,  Label responseLabel, I18nManager i18nManager) {
        boolean valid=true;
        var listInvalidVisa = new ArrayList<String>();
        var msg = "";
        if(newValue.isBlank()){
            valid = true;
        }else{
            var regex = "[A-Z+]{3}$";
            var listVisaHandled = newValue.split(", ");
            for (var visa: listVisaHandled) {
                if(!visa.matches(regex)){
                    listInvalidVisa.add(visa);
                    valid = false;
                }
            }
            msg = i18nManager.text(I18nKey.MSG_VALIDATED_EMPLOYEE_VISAS, listInvalidVisa);
        }
        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isProNumberNotExisted(Boolean isExisted, Label responseLabel, I18nManager i18nManager) {
        Boolean valid;
        var msg = "";
        if(isExisted){
            valid = false;
            msg = i18nManager.text(I18nKey.MSG_VALIDATED_NUMBER_EXISTED);
        }else{
            valid = true;
        }

        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isProNumberValidInput(String val, Label responseLabel, I18nManager i18nManager) {
        Boolean valid;
        var msg = "";
        if(val.isBlank()){
            msg = i18nManager.text(I18nKey.MSG_VALIDATED_BLANK);
            valid = false;
        }else{
            String exp = "[\\d+]{0,4}";
            valid = val.matches(exp);
            msg = i18nManager.text(I18nKey.MSG_VALIDATED_FORMAT_NUMBER);

        }

        return validationResponse(responseLabel, valid, msg);
    }


    public static ValidatedResponse isProNameValidInput(String val, Label responseLabel, I18nManager i18nManager) {
        Boolean valid;
        var msg = "";


        if(val.isBlank()){
            msg = i18nManager.text(I18nKey.MSG_VALIDATED_BLANK);
            valid = false;
        }else{
            String exp = "^[A-Za-z][A-Za-z\\d_\\s+]{0,50}$";
            /*if(val.matches(exp)){
                valid = null;
            }else{
                valid = false;
            }*/
            valid = val.matches(exp);
            msg = i18nManager.text(I18nKey.MSG_VALIDATED_TEXT_INPUT_FORMAT);
        }

        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isProCustomerValidInput(String val, Label responseLabel, I18nManager i18nManager) {
        Boolean valid;
        var msg = "";


        if(val.isBlank()){
            msg = i18nManager.text(I18nKey.MSG_VALIDATED_BLANK);
            valid = false;
        }else{
            String exp = "^[A-Za-z][A-Za-z\\d_\\s+]{0,50}$";
            /*if(val.matches(exp)){
                valid = null;
            }else{
                valid = false;
            }*/
            valid = val.matches(exp);
            msg = i18nManager.text(I18nKey.MSG_VALIDATED_TEXT_INPUT_FORMAT);
        }

        return validationResponse(responseLabel, valid, msg);
    }

    public static ValidatedResponse isDateValidInput(LocalDate endVal, LocalDate startVal, Label responseLabel, I18nManager i18nManager) {
        Boolean valid = true;
        var msg = "";
        if(endVal != null && startVal != null){
            if(endVal.isBefore(startVal)){
                valid = false;
                msg = i18nManager.text(I18nKey.MSG_VALIDATED_DATE);
            }
        }else{
            if (startVal == null){
                valid = false;
                msg = i18nManager.text(I18nKey.MSG_VALIDATED_NULL_DATE);
            }
        }
        return validationResponse(responseLabel, valid, msg);
    }


    public static ValidatedResponse validationResponse(Label responseLabel, Boolean valid,String msg){
        FormValidation.iconSize = 24;

        IconFontFX.register(GoogleMaterialDesignIcons.getIconFont());

        responseLabel.setText("");
        responseLabel.setGraphic(null);
        responseLabel.getStyleClass().clear();
        responseLabel.setWrapText(true);

        /*if (valid == null) {

            ImageView imageView = new ImageView(String.valueOf(FormValidation.class.getResource("/images/validate-response-loading.gif")));
            imageView.setFitWidth(24);
            imageView.setFitHeight(24);
            responseLabel.setGraphic(imageView);

            responseLabel.setText("");
            responseLabel.getStyleClass().add("validate-success");
            responseLabel.setTooltip(new Tooltip(msg));
            return new ValidatedResponse(responseLabel, null);
        } else*/
        if(valid){
            responseLabel.setText("");

            IconNode correctIcon = new IconNode(GoogleMaterialDesignIcons.CHECK_CIRCLE);
            correctIcon.setIconSize(iconSize);
            correctIcon.setFill(Color.web("#152769"));

            responseLabel.setGraphic(correctIcon);
            responseLabel.getStyleClass().add("validate-success");
            responseLabel.setTooltip(new Tooltip(msg));
            return new ValidatedResponse(responseLabel,true);
        } else{
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
