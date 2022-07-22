package com.elca.internship.client.utils;

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

    public void showErrorDialog(Exception e) {
        Alert alert = new Alert(this.type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(GuiUtil.getScreenWidth() * 0.6);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public void showInformationDialog(){
        Alert alert = new Alert(this.type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public Alert getConfirmationDeleteProjectDialog(){
        // Confirmation dialog for printing the transaction
        Alert alert = new Alert(this.type, this.contentText ,ButtonType.YES, ButtonType.NO);
        alert.setTitle(this.title);
        alert.setHeaderText(this.headerText);

        return alert;
    }
}
