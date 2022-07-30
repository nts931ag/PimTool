package com.elca.internship.client.common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Data;

import java.io.PrintWriter;
import java.io.StringWriter;

@Data
public class AlertDialog {
    private String title;
    private String headerText;
    private String contentText;
    private String exception;
    private Alert.AlertType type;
    

    public AlertDialog(String title, String headerText, String contentText, Alert.AlertType type) {
        this.title = title;
        this.headerText = headerText;
        this.contentText = contentText;
        this.type = type;
    }

    public Alert getConfirmationDeleteProjectDialog(){
        // Confirmation dialog for printing the transaction
        Alert alert = new Alert(this.type, this.contentText ,ButtonType.YES, ButtonType.NO);
        alert.setTitle(this.title);
        alert.setHeaderText(this.headerText);

        return alert;
    }
}
