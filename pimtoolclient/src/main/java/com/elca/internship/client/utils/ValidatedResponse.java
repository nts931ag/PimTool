package com.elca.internship.client.utils;

import javafx.scene.control.Label;
import lombok.Data;

@Data
public class ValidatedResponse {
    private Label responseLabel;
    private boolean isValid;

    public ValidatedResponse(Label responseLabel, boolean isValid) {
        this.responseLabel = responseLabel;
        this.isValid = isValid;
    }
}
