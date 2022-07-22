package com.elca.internship.client.utils;

import javafx.scene.control.Label;
import lombok.Data;

@Data
public class ValidatedResponse {
    private Label responseLabel;
    private Boolean isValid;

    public ValidatedResponse(Label responseLabel, Boolean isValid) {
        this.responseLabel = responseLabel;
        this.isValid = isValid;
    }
}
