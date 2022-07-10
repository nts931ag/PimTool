package com.elca.internship.client.Utils;

import javafx.scene.control.Label;
import lombok.Data;

@Data
public class ValidatedResponse {
    private boolean isValid;
    private Label responseLabel;

    public ValidatedResponse(Label responseLabel, boolean b) {
    }
}
